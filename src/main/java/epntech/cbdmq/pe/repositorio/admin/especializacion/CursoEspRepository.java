package epntech.cbdmq.pe.repositorio.admin.especializacion;

import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_MUY_GRANDE;
import static epntech.cbdmq.pe.constante.ArchivoConst.NO_ADJUNTO;
import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_PROCESO_ESPECIALIZACION;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.Requisito;
import epntech.cbdmq.pe.dominio.admin.especializacion.Curso;
import epntech.cbdmq.pe.dominio.admin.especializacion.CursoDocumento;
import epntech.cbdmq.pe.dominio.admin.especializacion.DocumentoCurso;
import epntech.cbdmq.pe.dominio.util.DatosFile;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository

public class CursoEspRepository {
	//Interfaz principal de JPA; utilizada para la persistencia de las aplicaciones. Esta clase es la encargada de gestionar las entidades
	@PersistenceContext
	private EntityManager entityManager;
	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;
	@Value("${spring.servlet.multipart.max-file-size}")
	public DataSize TAMAÑO_MÁXIMO;
	
	@Transactional
	public Curso insertarCursosDocumentosRequisitos(Curso curso, Set<Requisito> requisitos, List<MultipartFile> documentos, Long codTipoDocumento) {
		curso.setEstado("ACTIVO");
		curso.setApruebaCreacionCurso(false);
		entityManager.persist(curso);
		
		if(documentos.get(0).getSize() >= 1) {
			List<DatosFile> archivos = new ArrayList<>();
			try {
				archivos = guardarArchivo(documentos, PATH_PROCESO_ESPECIALIZACION, curso.getCodCursoEspecializacion().toString());
			} catch (Exception e) {
				HttpHeaders headers = new HttpHeaders();
				headers.add("errorHeader", e.getMessage());
			}
			
			Set<DocumentoCurso> docs = new HashSet<>();
			
			for (DatosFile datosFile : archivos) {
				DocumentoCurso dd = new DocumentoCurso();
				dd.setEstado("ACTIVO");
				dd.setCodTipoDocumento(codTipoDocumento);
				dd.setNombreDocumento(datosFile.getNombre());
				dd.setRuta(datosFile.getRuta());

				docs.add(dd);
			}

			for (DocumentoCurso documento : docs) {
				entityManager.persist(documento);
				
				CursoDocumento cursoDocumento = new CursoDocumento();
				cursoDocumento.setCodCursoEspecializacion(curso.getCodCursoEspecializacion());
				cursoDocumento.setCodDocumento(documento.getCodigoDocumento());
				entityManager.persist(cursoDocumento);
				
			}
		}
		
		return curso;
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
