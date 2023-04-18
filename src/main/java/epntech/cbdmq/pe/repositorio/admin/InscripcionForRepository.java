package epntech.cbdmq.pe.repositorio.admin;

import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_MUY_GRANDE;
import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_PROCESO_INSCRIPCION_FOR;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.dominio.admin.DatoPersonalDocumentoFor;
import epntech.cbdmq.pe.dominio.admin.DatosFile;
import epntech.cbdmq.pe.dominio.admin.DocumentoDatoPersonal;
import epntech.cbdmq.pe.dominio.admin.InscripcionFor;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@Transactional
public class InscripcionForRepository{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;

	@Value("${spring.servlet.multipart.max-file-size}")
	public DataSize TAMAÑO_MÁXIMO;
	
	public Integer insertarInscripcionConDocumentos(InscripcionFor inscripcion, List<MultipartFile> docsInscripcion)
			throws IOException, ArchivoMuyGrandeExcepcion, MessagingException {
	
		String sqlDatoPersonal = "INSERT INTO cbdmq.gen_dato_personal (apellido, cedula, cod_estacion, correo_personal, estado, fecha_nacimiento, nombre, num_telef_convencional, tipo_sangre, validacion_correo, cod_provincia_nacimiento"
				+"cod_unidad_gestion, genero, num_telef_celular, canton_nacimiento, reside_pais, cod_provincia_residencia, canton_residencia, calle_principal_residencia, calle_secundaria_residencia, numero_casa, colegio, tipo_nacionalidad, "
				+"tiene_merito_deportivo, tiene_merito_academico, nombre_titulo, pais_titulo, ciudad_titulo, merito_deportivo_descripcion, merito_academico_descripcion, pin_validacion_correo) "
				+ "VALUES (:apellido, :cedula, :cod_estacion, :correo_personal, :estado, :fecha_nacimiento, :nombre, :num_telef_convencional, :tipo_sangre, :validacion_correo, :cod_provincia_nacimiento "
				+ ":cod_unidad_gestion, :genero, :num_telef_celular, :canton_nacimiento, :reside_pais, :cod_provincia_residencia, :canton_residencia, :calle_principal_residencia, :calle_secundaria_residencia, :numero_casa, :colegio, :tipo_nacionalidad, "
				+ ":tiene_merito_deportivo, :tiene_merito_academico, :nombre_titulo, :pais_titulo, :ciudad_titulo, :merito_deportivo_descripcion, :merito_academico_descripcion, :pin_validacion_correo)";
		
		String sqlDocumento = "INSERT INTO cbdmq.gen_documento (autorizacion, cod_tipo_documento, descripcion, estado_validacion, codigo_unico_documento, nombre_documento, observaciones, ruta, estado) "
				+ "VALUES (:autorizacion, :tipo, :descripcion, :estadoValidacion, :codigoUnico, :nombre, :observaciones, :ruta, :estado)";
		
		String sqlDatoPersonalDocumento = "INSERT INTO cbdmq.gen_documento_dato_personal (cod_datos_personales, cod_documento) "
				+ "VALUES (:cod_datos_personales, :cod_documento)";
		

		entityManager.createNativeQuery(sqlDatoPersonal)
				.setParameter("apellido", inscripcion.getApellido())
				.setParameter("cedula", inscripcion.getCedula())
				.setParameter("cod_estacion", inscripcion.getCod_estacion())
				.setParameter("correo_personal", inscripcion.getCorreoPersonal())
				.setParameter("estado", inscripcion.getEstado())
				.setParameter("fecha_nacimiento", inscripcion.getFecha_nacimiento())
				.setParameter("nombre", inscripcion.getNombre())
				.setParameter("num_telef_convencional", inscripcion.getNum_telef_convencional())
				.setParameter("tipo_sangre", inscripcion.getTipo_sangre())
				.setParameter("validacion_correo", inscripcion.getValidacion_correo())
				.setParameter("cod_provincia_nacimiento", inscripcion.getCod_provincia_nacimiento())
				.setParameter("cod_unidad_gestion", inscripcion.getCod_unidad_gestion())
				.setParameter("genero", inscripcion.getGenero())
				.setParameter("num_telef_celular", inscripcion.getNum_telef_celular())
				.setParameter("canton_nacimiento", inscripcion.getCanton_nacimiento())
				.setParameter("reside_pais", inscripcion.getReside_pais())
				.setParameter("cod_provincia_residencia", inscripcion.getCod_provincia_residencia())
				.setParameter("canton_residencia", inscripcion.getCanton_residencia())
				.setParameter("calle_principal_residencia", inscripcion.getCalle_principal_residencia())
				.setParameter("calle_secundaria_residencia", inscripcion.getCalle_secundaria_residencia())
				.setParameter("numero_casa", inscripcion.getNumero_casa())
				.setParameter("colegio", inscripcion.getColegio())
				.setParameter("tipo_nacionalidad", inscripcion.getTipo_nacionalidad())
				.setParameter("tiene_merito_deportivo", inscripcion.getTiene_merito_deportivo())
				.setParameter("tiene_merito_academico", inscripcion.getTiene_merito_academico())
				.setParameter("nombre_titulo", inscripcion.getNombre_titulo())
				.setParameter("pais_titulo", inscripcion.getPais_titulo())
				.setParameter("ciudad_titulo", inscripcion.getCiudad_titulo())
				.setParameter("merito_deportivo_descripcion", inscripcion.getMerito_deportivo_descripcion())
				.setParameter("merito_academico_descripcion", inscripcion.getMerito_academico_descripcion())
				.setParameter("pin_validacion_correo", inscripcion.getPin_validacion_correo());

		entityManager.persist(inscripcion);
		
		// ARCHIVOS
		// guardamos archivos en el servidor

		List<DatosFile> archivos = new ArrayList<>();
		try {
			archivos = guardarArchivo(docsInscripcion, PATH_PROCESO_INSCRIPCION_FOR, inscripcion.getCodDatoPersonal().toString());
		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("errorHeader", e.getMessage());
		}
		Set<DocumentoDatoPersonal> dDatoPersonal = new HashSet<>();
		
		for (DatosFile datosFile : archivos) {
			DocumentoDatoPersonal dd = new DocumentoDatoPersonal();
			dd.setEstado("ACTIVO");
			dd.setNombre(datosFile.getNombre());
			dd.setRuta(datosFile.getRuta());

			dDatoPersonal.add(dd);
		}
		
		//
		Set<DocumentoDatoPersonal> documentos = new HashSet<>();
		for (DocumentoDatoPersonal documento : dDatoPersonal) {
			DocumentoDatoPersonal documentoDP = new DocumentoDatoPersonal();
			entityManager.createNativeQuery(sqlDocumento)
					.setParameter("autorizacion", documento.getAutorizacion())
					.setParameter("tipo", documento.getTipo())
					.setParameter("descripcion", documento.getDescripcion())
					.setParameter("estadoValidacion", documento.getEstadoValidacion())
					.setParameter("codigoUnico", documento.getCodigoUnico())
					.setParameter("nombre", documento.getNombre())
					.setParameter("observaciones", documento.getObservaciones())
					.setParameter("ruta", documento.getRuta())
					.setParameter("estado", documento.getEstado());
			entityManager.persist(documento);
			documentoDP = documento;
			documentos.add(documentoDP);
		}

		Integer codDatoPersonal = inscripcion.getCodDatoPersonal();
		
		for (DocumentoDatoPersonal elemento : documentos) {
			DatoPersonalDocumentoFor datoPersonalDocumentoFor = new DatoPersonalDocumentoFor();
			
			entityManager.createNativeQuery(sqlDatoPersonalDocumento)
					.setParameter("cod_datos_personales", codDatoPersonal)
					.setParameter("cod_documento", elemento.getCodigoDocumento());
			
			datoPersonalDocumentoFor.setCod_datos_personales(codDatoPersonal);
			datoPersonalDocumentoFor.setCod_documento(elemento.getCodigoDocumento());
			entityManager.persist(datoPersonalDocumentoFor);
		}		
				
		
		return inscripcion.getCodDatoPersonal();
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
