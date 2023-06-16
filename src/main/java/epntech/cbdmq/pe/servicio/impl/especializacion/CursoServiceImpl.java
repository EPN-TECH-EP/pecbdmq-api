package epntech.cbdmq.pe.servicio.impl.especializacion;

import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_PROCESO_ESPECIALIZACION;
import static epntech.cbdmq.pe.constante.MensajesConst.ESTADO_INCORRECTO;
import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_MUY_GRANDE;
import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_BAJAS;

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

import epntech.cbdmq.pe.dominio.admin.BajaDocumento;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.Requisito;
import epntech.cbdmq.pe.dominio.admin.especializacion.Curso;
import epntech.cbdmq.pe.dominio.admin.especializacion.CursoDocumento;
import epntech.cbdmq.pe.dominio.admin.especializacion.CursoRequisito;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
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
	
	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;
	@Value("${spring.servlet.multipart.max-file-size}")
	public DataSize TAMAÑO_MAXIMO;

	@Override
	public Curso save(Curso obj, Set<Requisito> requisitos, List<MultipartFile> documentos, Long codTipoDocumento) {
		Curso cc = new Curso();
		cc = cursoEspRepository.insertarCursosDocumentosRequisitos(obj, requisitos, documentos, codTipoDocumento);

		return cursoRepository.findById(cc.getCodCursoEspecializacion()).get();
	}

	@Override
	public Curso update(Curso objActualizado) {
		// TODO Auto-generated method stub
		return cursoRepository.save(objActualizado);
	}

	@Override
	public List<Curso> listarAll() {
		// TODO Auto-generated method stub
		return cursoRepository.findAll();
	}

	@Override
	public Optional<Curso> getById(Long id) {
		// TODO Auto-generated method stub
		return cursoRepository.findById(id);
	}

	@Override
	public CursoDocumento updateEstadoAprobadoValidado(Boolean estadoAprobado, Boolean estadoValidado,
			String observaciones, Long codCursoEspecializacion, Long codDocumento) {
		// TODO Auto-generated method stub
		cursoDocumentoRepository.updateEstadoAprobado(estadoAprobado, estadoValidado, observaciones,
				codCursoEspecializacion, codDocumento);
		cursoDocumentoRepository.validaDocumentosCurso(codCursoEspecializacion);
		
		return cursoDocumentoRepository.findByCodCursoEspecializacionAndCodDocumento(codCursoEspecializacion,
				codDocumento);
	}

	@Override
	public Curso updateEstadoProceso(Long estado, Long codCurso) throws DataException {
		int result = cursoRepository.updateEstadoProceso(estado, codCurso);
		if (result == 1)
			return cursoRepository.findById(codCurso).get();
		else
			throw new DataException(ESTADO_INCORRECTO);
	}

	@Override
	public Curso updateRequisitos(Long codCursoEspecializacion, List<Requisito> requisitos) {

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
	public Documento updateDocumento(Long codDocumento, MultipartFile archivo) throws IOException {
		Optional<Documento> documentoOptional;
		Documento documento = new Documento();

		documentoOptional = documentoRepository.findById(codDocumento.intValue());
		documento = documentoOptional.get();

		Path ruta = Paths.get(documento.getRuta());

		//System.out.println("ruta: " + ruta);
		try {
			
			if (Files.exists(ruta)) {
				Files.delete(ruta);
			}
			MultipartFile multipartFile = archivo;
			if (multipartFile.getSize() > TAMAÑO_MAXIMO.toBytes()) {
				throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
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
			cursoDocumento.setCodDocumento((long) documento.getCodigo());
			cursoDocumentoRepository.save(cursoDocumento);
			
		}

	}
	
	private String ruta(Long codigo) {

		String resultado = null;
		resultado = ARCHIVOS_RUTA + PATH_PROCESO_ESPECIALIZACION + codigo + "/";
		return resultado;
	}

	@Override
	public void delete(Long codCursoEspecializacion) {
		cursoRepository.deleteById(codCursoEspecializacion);
		
	}

}
