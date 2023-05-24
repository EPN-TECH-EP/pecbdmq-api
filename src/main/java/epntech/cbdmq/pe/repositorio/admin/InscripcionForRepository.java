package epntech.cbdmq.pe.repositorio.admin;

import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_MUY_GRANDE;
import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_PROCESO_INSCRIPCION_FOR;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import epntech.cbdmq.pe.dominio.admin.DocumentoDatoPersonal;
import epntech.cbdmq.pe.dominio.admin.DocumentoPostulante;
import epntech.cbdmq.pe.dominio.admin.InscripcionFor;
import epntech.cbdmq.pe.dominio.admin.PostulanteDocumentoFor;
import epntech.cbdmq.pe.dominio.admin.PostulanteFor;
import epntech.cbdmq.pe.dominio.util.DatosFile;
import epntech.cbdmq.pe.dominio.util.InscripcionResult;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;

@Repository
@Transactional
public class InscripcionForRepository{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;

	@Value("${spring.servlet.multipart.max-file-size}")
	public DataSize TAMAÑO_MÁXIMO;
	
	public InscripcionResult insertarInscripcionConDocumentos(InscripcionFor inscripcion, List<MultipartFile> docsInscripcion)
			throws IOException, ArchivoMuyGrandeExcepcion, MessagingException, ParseException {
	
		String sqlDatoPersonal = "INSERT INTO cbdmq.gen_dato_personal (apellido, cedula, cod_estacion, correo_personal, estado, fecha_nacimiento, nombre, num_telef_convencional, tipo_sangre, validacion_correo, cod_provincia_nacimiento"
				+"cod_unidad_gestion, genero, num_telef_celular, canton_nacimiento, reside_pais, cod_provincia_residencia, canton_residencia, calle_principal_residencia, calle_secundaria_residencia, numero_casa, colegio, tipo_nacionalidad, "
				+"tiene_merito_deportivo, tiene_merito_academico, nombre_titulo, pais_titulo, ciudad_titulo, merito_deportivo_descripcion, merito_academico_descripcion, pin_validacion_correo) "
				+ "VALUES (:apellido, :cedula, :cod_estacion, :correo_personal, :estado, :fecha_nacimiento, :nombre, :num_telef_convencional, :tipo_sangre, :validacion_correo, :cod_provincia_nacimiento "
				+ ":cod_unidad_gestion, :genero, :num_telef_celular, :canton_nacimiento, :reside_pais, :cod_provincia_residencia, :canton_residencia, :calle_principal_residencia, :calle_secundaria_residencia, :numero_casa, :colegio, :tipo_nacionalidad, "
				+ ":tiene_merito_deportivo, :tiene_merito_academico, :nombre_titulo, :pais_titulo, :ciudad_titulo, :merito_deportivo_descripcion, :merito_academico_descripcion, :pin_validacion_correo)";
		
		String sqlPostulante = "INSERT INTO cbdmq.gen_postulante (cod_datos_personales, estado, fecha_postulacion, edad_postulacion) "
				+ "VALUES (:cod_datos_personales, :estado, :fecha_postulacion, :edad_postulacion)";
		
		String sqlDocumento = "INSERT INTO cbdmq.gen_documento (autorizacion, cod_tipo_documento, descripcion, estado_validacion, codigo_unico_documento, nombre_documento, observaciones, ruta, estado) "
				+ "VALUES (:autorizacion, :tipo, :descripcion, :estadoValidacion, :codigoUnico, :nombre, :observaciones, :ruta, :estado)";
		
		String sqlPostulanteDocumento = "INSERT INTO cbdmq.gen_postulante_documento (cod_postulante, cod_documento) "
				+ "VALUES (:cod_postulante, :cod_documento)";
		

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
				//.setParameter("canton_nacimiento", inscripcion.getCanton_nacimiento())
				.setParameter("reside_pais", inscripcion.getReside_pais())
				.setParameter("cod_provincia_residencia", inscripcion.getCod_provincia_residencia())
				//.setParameter("canton_residencia", inscripcion.getCanton_residencia())
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
		
		//POSTULANTE
		//System.out.println("inscripcion.getFecha_nacimiento(): " + inscripcion.getFecha_nacimiento());
		
		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("cbdmq.get_edad");
		query.registerStoredProcedureParameter("fecha", Date.class, ParameterMode.IN);
		query.setParameter("fecha", inscripcion.getFecha_nacimiento());
		query.execute();
		Object edad = query.getSingleResult();
		
		//System.out.println("edad: " + edad);
		
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaPostulacion = formatter.parse(formatter.format(date));

		PostulanteFor postulante = new PostulanteFor();
		postulante.setCodDatoPersonal(inscripcion.getCodDatoPersonal());
		postulante.setEstado(inscripcion.getEstado());
		postulante.setFechaPostulacion(fechaPostulacion);
		postulante.setEdadPostulacion(Integer.parseInt(edad.toString()));
		
		entityManager.createNativeQuery(sqlPostulante)
			.setParameter("cod_datos_personales", postulante.getCodDatoPersonal())
			.setParameter("estado", postulante.getEstado())
			.setParameter("fecha_postulacion", postulante.getFechaPostulacion())
			.setParameter("edad_postulacion", postulante.getEdadPostulacion());

		entityManager.persist(postulante);

		// ARCHIVOS
		// guardamos archivos en el servidor

		List<DatosFile> archivos = new ArrayList<>();
		try {
			archivos = guardarArchivo(docsInscripcion, PATH_PROCESO_INSCRIPCION_FOR, inscripcion.getCodDatoPersonal().toString());
		} catch (Exception e) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("errorHeader", e.getMessage());
		}
		Set<DocumentoPostulante> dPostulante = new HashSet<>();
		
		for (DatosFile datosFile : archivos) {
			DocumentoPostulante dd = new DocumentoPostulante();
			dd.setEstado("ACTIVO");
			dd.setNombre(datosFile.getNombre());
			dd.setRuta(datosFile.getRuta());

			dPostulante.add(dd);
		}
		
		//CREA LOS DOCUMENTOS EN LA TABLA GEN_DOCUMENTO
		Set<DocumentoPostulante> documentos = new HashSet<>();
		for (DocumentoPostulante documento : dPostulante) {
			DocumentoPostulante documentoDP = new DocumentoPostulante();
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

		Integer codPostulante = postulante.getCodPostulante();
		//System.out.println("codPostulante: " + codPostulante);
		
		for (DocumentoPostulante elemento : documentos) {
			PostulanteDocumentoFor postulanteDocumentoFor = new PostulanteDocumentoFor();
			//System.out.println("elemento.getCodigoDocumento(): " + elemento.getCodigoDocumento());
			entityManager.createNativeQuery(sqlPostulanteDocumento)
					.setParameter("cod_postulante", codPostulante)
					.setParameter("cod_documento", elemento.getCodigoDocumento());
			
			postulanteDocumentoFor.setCod_postulante(codPostulante);
			postulanteDocumentoFor.setCod_documento(elemento.getCodigoDocumento());
			entityManager.persist(postulanteDocumentoFor);
		}		
				
		//inserta los requisitos por postulante para la validación
		StoredProcedureQuery sql = entityManager.createStoredProcedureQuery("cbdmq.insert_requisitos");
		sql.registerStoredProcedureParameter("postulante", Integer.class, ParameterMode.IN);
		sql.setParameter("postulante", codPostulante);
		sql.execute();
		sql.getSingleResult();
		
		
		InscripcionResult result = new InscripcionResult();
		result.setCod_datos_personales(postulante.getCodDatoPersonal());
		result.setCod_postulante(codPostulante);
		
		return result;
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
