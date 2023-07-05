package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_MUY_GRANDE;
import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_PROCESO_FORMACION_JUNTA;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.JuntaFormacion;
import epntech.cbdmq.pe.dominio.util.DatosFile;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.JuntaFormacionRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.servicio.JuntaFormacionService;

@Service
public class JuntaFormacionServiceImpl implements JuntaFormacionService {
	
	@Autowired
	private JuntaFormacionRepository juntaFormacionRepository;
	@Autowired
	private DocumentoRepository documentoRepository;
	@Autowired
	private PeriodoAcademicoRepository periodoAcademicoRepository;
	
	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;
	@Value("${spring.servlet.multipart.max-file-size}")
	public DataSize TAMAÑO_MÁXIMO;

	@Override
	public List<DatosFile> save(List<MultipartFile> archivos,  Long codTipoDocumento) throws DataException, IOException, ArchivoMuyGrandeExcepcion{
		
		return guardarArchivo(archivos, codTipoDocumento);
	}

	@Override
	public void delete(Long codJunta) throws DataException {
		
		Optional<JuntaFormacion> juntaFormacion = juntaFormacionRepository.findById(codJunta);
		if (juntaFormacion.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);
		
		Optional<Documento> documento = documentoRepository.findById(juntaFormacion.get().getDocumento().getCodDocumento());
				
		Path ruta = Paths.get(documento.get().getRuta());

		// System.out.println("ruta: " + ruta);
		if (Files.exists(ruta)) {
			try {
				// System.out.println("ruta" + ruta);
				Files.delete(ruta);
				
				juntaFormacionRepository.deleteById(codJunta);
				//documentoRepository.deleteById(documento.get().getCodigo());
			} catch (Exception e) {

				throw new DataException(e.getMessage());
				// e.printStackTrace();
			}

		}
	}

	public List<DatosFile> guardarArchivo(List<MultipartFile> archivo, Long tipoDocumento)
			throws IOException, ArchivoMuyGrandeExcepcion {
		String resultado;
		Integer periodo = periodoAcademicoRepository.getPAActive();

		resultado = ruta(periodo.toString());
		Path ruta = Paths.get(resultado).toAbsolutePath().normalize();

		if (!Files.exists(ruta)) {
			Files.createDirectories(ruta);
		}

		List<DatosFile> lista = new ArrayList<>();

		for (Iterator iterator = archivo.iterator(); iterator.hasNext();) {
			DatosFile documentos = new DatosFile();
			MultipartFile multipartFile = (MultipartFile) iterator.next();
			if (multipartFile.getSize() > TAMAÑO_MÁXIMO.toBytes()) {
				throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
			}

			Files.copy(multipartFile.getInputStream(), ruta.resolve(multipartFile.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			// LOGGER.info("Archivo guardado: " + resultado +

			documentos.setRuta(resultado + multipartFile.getOriginalFilename());
			documentos.setNombre(multipartFile.getOriginalFilename());
			lista.add(documentos);
			
			Documento documento = new Documento();
			documento.setEstado("ACTIVO");
			documento.setTipo(tipoDocumento.intValue());
			documento.setNombre(multipartFile.getOriginalFilename());
			documento.setRuta(resultado + multipartFile.getOriginalFilename());
			documento = documentoRepository.save(documento);
			
			JuntaFormacion juntaFormacion = new JuntaFormacion();
			juntaFormacion.setDocumento(documento);
			juntaFormacion.setCodPeriodoAcademico((long) periodo);
			juntaFormacionRepository.save(juntaFormacion);
		}

		return lista;
	}

	private String ruta(String id) {

		String resultado = null;
		resultado = ARCHIVOS_RUTA + PATH_PROCESO_FORMACION_JUNTA + id + "/";
		return resultado;
	}

	@Override
	public List<JuntaFormacion> getDocumentos() {
		// TODO Auto-generated method stub
		return juntaFormacionRepository.findByCodPeriodoAcademico((long) periodoAcademicoRepository.getPAActive());
	}
}
