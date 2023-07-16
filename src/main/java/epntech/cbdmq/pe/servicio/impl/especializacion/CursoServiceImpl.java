package epntech.cbdmq.pe.servicio.impl.especializacion;

import static epntech.cbdmq.pe.constante.ArchivoConst.*;
import static epntech.cbdmq.pe.constante.MensajesConst.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.admin.BajaDocumento;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.Requisito;
import epntech.cbdmq.pe.dominio.admin.especializacion.Curso;
import epntech.cbdmq.pe.dominio.admin.especializacion.CursoDocumento;
import epntech.cbdmq.pe.dominio.admin.especializacion.CursoRequisito;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.AulaRepository;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.CursoDocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.CursoEspRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.CursoRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.CursoRequisitoRepository;
import epntech.cbdmq.pe.servicio.especializacion.CursoService;

@Service
public class CursoServiceImpl implements CursoService {

	@Autowired
	private CursoRepository cursoRepository;
	@Autowired
	private CursoEspRepository cursoEspRepository;
	@Autowired
	private CursoDocumentoRepository cursoDocumentoRepository;
	@Autowired
	private CursoRequisitoRepository cursoRequisitoRepository;
	@Autowired
	private DocumentoRepository documentoRepository;
	@Autowired
	private AulaRepository aulaRepository;
	
	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;
	@Value("${spring.servlet.multipart.max-file-size}")
	public DataSize TAMAÑO_MAXIMO;

	@Override
	public Curso save(Curso obj, Set<Requisito> requisitos, List<MultipartFile> documentos, Long codTipoDocumento) throws DataException {
		Optional<Aula> aulaOptional = aulaRepository.findById(obj.getCodAula());
		if(aulaOptional.isEmpty())
				throw new DataException(AULA_NO_EXISTE);
		
		Curso cc = new Curso();
		cc = cursoEspRepository.insertarCursosDocumentosRequisitos(obj, requisitos, documentos, codTipoDocumento);

		return cursoRepository.findById(cc.getCodCursoEspecializacion()).get();
	}

	@Override
	public Curso update(Curso objActualizado) throws DataException {
		Optional<Curso> cursoOptional = cursoRepository.findById(objActualizado.getCodCursoEspecializacion());
		if(cursoOptional.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);
		
		return cursoRepository.save(objActualizado);
	}

	@Override
	public List<Curso> listarAll() {
		// TODO Auto-generated method stub
		return cursoRepository.findAll();
	}

	@Override
	public Optional<Curso> getById(Long id) throws DataException {
		Optional<Curso> cursoOptional = cursoRepository.findById(id);
		if(cursoOptional.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);
			
		return cursoRepository.findById(id);
	}

	@Override
	public CursoDocumento updateEstadoAprobadoValidado(Boolean estadoAprobado, Boolean estadoValidado,
			String observaciones, Long codCursoEspecializacion, Long codDocumento) throws DataException {
		Optional<Curso> cursoOptional = cursoRepository.findById(codCursoEspecializacion);
		if(cursoOptional.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);
			
		Optional<Documento> documentoOptional = documentoRepository.findById(codDocumento.intValue());
		if(documentoOptional.isEmpty())
			throw new DataException(DOCUMENTO_NO_EXISTE);
		
		cursoDocumentoRepository.updateEstadoAprobado(estadoAprobado, estadoValidado, observaciones,
				codCursoEspecializacion, codDocumento);
		cursoRepository.validaDocumentosCursoEspecializacion(codCursoEspecializacion);
		
		return cursoDocumentoRepository.findByCodCursoEspecializacionAndCodDocumento(codCursoEspecializacion,
				codDocumento);
	}

	@Override
	public Curso updateEstadoProceso(Long estado, Long codCurso) throws DataException {
		Optional<Curso> cursoOptional = cursoRepository.findById(codCurso);
		if(cursoOptional.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);
			
		int result = cursoRepository.updateEstadoProceso(estado, codCurso);
		if (result == 1)
			return cursoRepository.findById(codCurso).get();
		else
			throw new DataException(ESTADO_INCORRECTO);
	}

	@Override
	public Curso updateRequisitos(Long codCursoEspecializacion, List<Requisito> requisitos) throws DataException {

		Optional<Curso> cursoOptional = cursoRepository.findById(codCursoEspecializacion);
		if(cursoOptional.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);
			
		Optional<CursoRequisito> cursoRequisito = cursoRequisitoRepository
				.findFirstByCodCursoEspecializacion(codCursoEspecializacion);
		if (cursoRequisito.isPresent())
			cursoRequisitoRepository.deleteByCodCursoEspecializacion(codCursoEspecializacion);

		for (Requisito requisito : requisitos) {
			CursoRequisito cr = new CursoRequisito();
			cr.setCodCursoEspecializacion(codCursoEspecializacion);
			cr.setCodRequisito((long) requisito.getCodigoRequisito());

			cursoRequisitoRepository.save(cr);
		}

		return cursoRepository.findById(codCursoEspecializacion).get();
	}

	@Override
	public Documento updateDocumento(Long codDocumento, MultipartFile archivo) throws IOException, DataException {
		Optional<Documento> documentoOptional;
		documentoOptional = documentoRepository.findById(codDocumento.intValue());
		if(documentoOptional.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);
		
		Documento documento = new Documento();

		documentoOptional = documentoRepository.findById(codDocumento.intValue());
		documento = documentoOptional.get();

		Path ruta = Paths.get(documento.getRuta());

		System.out.println("ruta: " + ruta);
		try {
			
			if (Files.exists(ruta)) {
				Files.delete(ruta);
			}
	
			MultipartFile multipartFile = archivo;
			if (multipartFile.getSize() > TAMAÑO_MAXIMO.toBytes()) {
				throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
			}
			if (!Files.exists(ruta)) {
				Files.createDirectories(ruta);
			}
			Files.copy(multipartFile.getInputStream(), ruta.getParent().resolve(multipartFile.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			
			documento.setNombre(archivo.getOriginalFilename());
			documento.setRuta(ruta.getParent() + "/" + archivo.getOriginalFilename());
			documentoRepository.save(documento);

		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}

		return documento;
	}

	@Override
	public Optional<Curso> uploadDocumentos(Long codCursoEspecializacion, List<MultipartFile> archivos, Long codTipoDocumento) throws IOException, ArchivoMuyGrandeExcepcion {
		guardarDocumentos(archivos, codCursoEspecializacion, codTipoDocumento);
		
		return cursoRepository.findById(codCursoEspecializacion);
	}
	
	public void guardarDocumentos(List<MultipartFile> archivos, Long codCursoEspecializacion, Long tipoDocumento)
			throws IOException, ArchivoMuyGrandeExcepcion {
		String resultado;

		resultado = ruta(codCursoEspecializacion);
		Path ruta = Paths.get(resultado);

		if (!Files.exists(ruta)) {
			Files.createDirectories(ruta);
		}

		for (Iterator iterator = archivos.iterator(); iterator.hasNext();) {
			
			MultipartFile multipartFile = (MultipartFile) iterator.next();
			if (multipartFile.getSize() > TAMAÑO_MAXIMO.toBytes()) {
				throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
			}

			Files.copy(multipartFile.getInputStream(), ruta.resolve(multipartFile.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			// LOGGER.info("Archivo guardado: " + resultado +
			
			Documento documento = new Documento();
			documento.setEstado("ACTIVO");
			documento.setTipo(tipoDocumento.intValue());
			documento.setNombre(multipartFile.getOriginalFilename());
			documento.setRuta(resultado + multipartFile.getOriginalFilename());
			documento = documentoRepository.save(documento);
			
			CursoDocumento cursoDocumento = new CursoDocumento();
			cursoDocumento.setCodCursoEspecializacion(codCursoEspecializacion);
			cursoDocumento.setCodDocumento((long) documento.getCodDocumento());
			cursoDocumentoRepository.save(cursoDocumento);
			
		}

	}
	
	private String ruta(Long codigo) {

		String resultado = null;
		resultado = ARCHIVOS_RUTA + PATH_PROCESO_ESPECIALIZACION + codigo + "/";
		return resultado;
	}

	@Override
	public void delete(Long codCursoEspecializacion) throws DataException {
		Optional<Curso> cursoOptional = cursoRepository.findById(codCursoEspecializacion);
		if(cursoOptional.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);
		
		cursoRepository.deleteById(codCursoEspecializacion);
		
	}

	@Override
	public Boolean cumpleMinimoAprobadosCurso(Long codCursoEspecializacion) throws DataException {
		Optional<Curso> cursoOptional = cursoRepository.findById(codCursoEspecializacion);
		if(cursoOptional.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);
		
		return cursoRepository.cumplePorcentajeMinimoAprobadosCurso(codCursoEspecializacion);
	}

	@Override
	public void deleteDocumento(Long codCursoEspecializacion, Long codDocumento) throws DataException {

		Optional<Curso> cursoOptional = cursoRepository.findById(codCursoEspecializacion);
		if(cursoOptional.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);
		
		Optional<Documento> documentoOptional;
		Documento documento = new Documento();

		// System.out.println("id: " + codDocumento);
		documentoOptional = documentoRepository.findById(codDocumento.intValue());
		if(documentoOptional.isEmpty())
			throw new DataException(DOCUMENTO_NO_EXISTE);
			
		documento = documentoOptional.get();

		Path ruta = Paths.get(documento.getRuta());

		// System.out.println("ruta: " + ruta);
		if (Files.exists(ruta)) {
			try {
				// System.out.println("ruta" + ruta);
				Files.delete(ruta);
				cursoDocumentoRepository.deleteByCodCursoEspecializacionAndCodDocumento(codCursoEspecializacion, codDocumento);
				documentoRepository.deleteById(codDocumento.intValue());
				
			} catch (Exception e) {

				throw new DataException(e.getMessage());
				// e.printStackTrace();
			}

		}
		else {
			throw new DataException(PATH_NO_EXISTE);
		}
		
	}

}
