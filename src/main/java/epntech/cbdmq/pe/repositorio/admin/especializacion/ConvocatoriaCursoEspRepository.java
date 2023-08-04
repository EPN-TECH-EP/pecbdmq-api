package epntech.cbdmq.pe.repositorio.admin.especializacion;

import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_MUY_GRANDE;
import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_PROCESO_ESPECIALIZACION;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import epntech.cbdmq.pe.dominio.admin.especializacion.CursoDocumento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.especializacion.ConvocatoriaCurso;
import epntech.cbdmq.pe.dominio.admin.especializacion.ConvocatoriaDocumentoEsp;
import epntech.cbdmq.pe.dominio.util.DatosFile;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ConvocatoriaCursoEspRepository {

	@Autowired
	private ConvocatoriaCursoRepository convocatoriaCursoRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;
	@Value("${spring.servlet.multipart.max-file-size}")
	public DataSize TAMAÑO_MÁXIMO;
	
	@Transactional
	public ConvocatoriaCurso insertarConvocatoriaDocumentos(ConvocatoriaCurso convocatoriaCurso, List<MultipartFile> archivos) {
		
		LocalDate fecha = LocalDate.now();
		convocatoriaCurso.setFechaActual(fecha);
		convocatoriaCurso.setCodigoUnicoConvocatoria(convocatoriaCursoRepository.getId("CE"));
		entityManager.persist(convocatoriaCurso);
		
		List<DatosFile> archivosConvocatoria = new ArrayList<>();
		try {
			archivosConvocatoria = guardarArchivo(archivos, PATH_PROCESO_ESPECIALIZACION, convocatoriaCurso.getCodCursoEspecializacion().toString());
		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("errorHeader", e.getMessage());
		}
		Set<Documento> documentos = new HashSet<>();
		
		for (DatosFile datosFile : archivosConvocatoria) {
			Documento documento = new Documento();
			documento.setEstado("ACTIVO");
			documento.setNombre(datosFile.getNombre());
			documento.setRuta(datosFile.getRuta());

			documentos.add(documento);
			
			entityManager.persist(documento);
		}
		
		for (Documento elemento : documentos) {
			ConvocatoriaDocumentoEsp convocatoriaDocumentoEsp = new ConvocatoriaDocumentoEsp();
			CursoDocumento cursoDocumentoGenerico= new  CursoDocumento();
			
			convocatoriaDocumentoEsp.setCodConvocatoria(convocatoriaCurso.getCodConvocatoria().intValue());
			convocatoriaDocumentoEsp.setCodDocumento(elemento.getCodDocumento());

			cursoDocumentoGenerico.setCodDocumento(Long.valueOf(elemento.getCodDocumento()));
			cursoDocumentoGenerico.setCodCursoEspecializacion(convocatoriaCurso.getCodCursoEspecializacion());
			entityManager.persist(convocatoriaDocumentoEsp);
			entityManager.persist(cursoDocumentoGenerico);

		}		
		
		return convocatoriaCurso;
	}
	
	public List<DatosFile> guardarArchivo(List<MultipartFile> archivo, String proceso, String id)
			throws IOException, ArchivoMuyGrandeExcepcion {
		String resultado;

		resultado = ruta(proceso, id);
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
		}

		return lista;
	}

	private String ruta(String proceso, String id) {

		String resultado = null;
		resultado = ARCHIVOS_RUTA + proceso + id + "/";
		return resultado;
	}
}
