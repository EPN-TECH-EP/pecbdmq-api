package epntech.cbdmq.pe.repositorio.admin;

import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_MUY_GRANDE;
import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SUBJECT_CONVOCATORIA;
import static epntech.cbdmq.pe.constante.ArchivoConst.*;

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
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.ConvocatoriaDocumentoFor;
import epntech.cbdmq.pe.dominio.admin.ConvocatoriaFor;
import epntech.cbdmq.pe.dominio.admin.ConvocatoriaRequisito;
import epntech.cbdmq.pe.dominio.admin.DatosFile;
import epntech.cbdmq.pe.dominio.admin.DocumentoFor;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademico;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademicoDocumentoFor;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademicoFor;
import epntech.cbdmq.pe.dominio.admin.Requisito;
import epntech.cbdmq.pe.dominio.admin.RequisitoFor;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.servicio.EmailService;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;

@Repository
@Transactional
public class ConvocatoriaForUpdRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private EmailService emailService;

	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;

	@Value("${spring.servlet.multipart.max-file-size}")
	public DataSize TAMAÑO_MÁXIMO;
	
	@Value("${server.port}")
	public String SERVER_PORT;

	@Value("${eureka.instance.hostname}")
	public String HOSTNAME;

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
	
	public PeriodoAcademicoFor actualizarConvocatoriaConDocumentos(ConvocatoriaFor convocatoria,
			Set<RequisitoFor> requisitos, List<MultipartFile> docsConvocatoria, DocumentoFor documentosFor)
			throws IOException, ArchivoMuyGrandeExcepcion, MessagingException, ParseException {
		
		String sqlConvocatoriaRequisito = "INSERT INTO cbdmq.gen_convocatoria_requisito (cod_convocatoria, cod_requisito) "
				+ "VALUES (:cod_convocatoria, :cod_requisito)";
		
		String sqlConvocatoriaRequisitoDel = "delete from ConvocatoriaRequisito e where e.cod_convocatoria = :cod_convocatoria ";


		// CONVOCATORIA

		ConvocatoriaFor entidad = entityManager.find(ConvocatoriaFor.class, convocatoria.getCodConvocatoria());

		Date fechaIni = new Date();
		fechaIni = convocatoria.getFechaInicioConvocatoria();
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
		String fechaFormateada = formateador.format(fechaIni); 
		
		
		// Modificar la entidad
		entidad.setCorreo(convocatoria.getCorreo());
		entidad.setCupoHombres(convocatoria.getCupoHombres());
		entidad.setCupoMujeres(convocatoria.getCupoMujeres());
		entidad.setEstado(convocatoria.getEstado());
		entidad.setFechaFinConvocatoria(convocatoria.getFechaFinConvocatoria());
		entidad.setFechaInicioConvocatoria(formateador.parse(fechaFormateada));
		entidad.setHoraFinConvocatoria(convocatoria.getHoraFinConvocatoria());
		entidad.setHoraInicioConvocatoria(convocatoria.getHoraInicioConvocatoria());
		entidad.setNombre(convocatoria.getNombre());

		// Llamar a merge() para actualizar la entidad en la base de datos
		entityManager.merge(entidad);

		Integer codConvocatoria = convocatoria.getCodConvocatoria();
		
		// ARCHIVOS CONVOCATORIA
		// guardamos archivos de la convocatoria en el servidor

		if (!(docsConvocatoria == null))
		{
			List<DatosFile> archivosConvocatoria = new ArrayList<>();
			try {
				archivosConvocatoria = guardarArchivo(docsConvocatoria, PATH_PROCESO_CONVOCATORIA, convocatoria.getCodConvocatoria().toString());
			} catch (Exception e) {
				HttpHeaders headers = new HttpHeaders();
				headers.add("errorHeader", e.getMessage());
			}
			Set<DocumentoFor> dConvocatoria = new HashSet<>();
			
			for (DatosFile datosFile : archivosConvocatoria) {
				DocumentoFor dd = new DocumentoFor();
				dd.setEstado("ACTIVO");
				dd.setNombre(datosFile.getNombre());
				dd.setRuta(datosFile.getRuta());
	
				dConvocatoria.add(dd);
			}
			
			DocumentoFor entidadDocumento = entityManager.find(DocumentoFor.class, documentosFor.getCodigoDocumento());		
			
			Set<DocumentoFor> documentos = new HashSet<>();
			for (DocumentoFor documento : dConvocatoria) {
				DocumentoFor documentoFor = new DocumentoFor();
				
				entidadDocumento.setNombre(documento.getNombre());
				entidadDocumento.setRuta(documento.getRuta());
				entityManager.merge(entidadDocumento);
				
				documentoFor = documento;
				documentos.add(documentoFor);
			}
		}

		
		// REQUISITOS
		
		if(requisitos.size() >= 1)
		{
		
	    	Query sqlQuery = entityManager.createQuery(sqlConvocatoriaRequisitoDel);
	    	sqlQuery.setParameter("cod_convocatoria", convocatoria.getCodConvocatoria());
			sqlQuery.executeUpdate();

			for (RequisitoFor elemento : requisitos) {
				ConvocatoriaRequisito cr = new ConvocatoriaRequisito();
				cr.setCod_convocatoria(convocatoria.getCodConvocatoria());
				cr.setCod_requisito(elemento.getCodigoRequisito());
				
				entityManager.createNativeQuery(sqlConvocatoriaRequisito)
						.setParameter("cod_convocatoria", convocatoria.getCodConvocatoria())
						.setParameter("cod_requisito", elemento.getCodigoRequisito());
				entityManager.persist(cr);
			}
		}
		
		//ENVIO DE EMAIL CON DOCUMENTO CONVOCATORIA
		if (docsConvocatoria != null)
		{
			String link = HOSTNAME + ":" + SERVER_PORT + "/link/" + documentosFor.getCodigoDocumento();
			
			String mensaje = "Se adjunta link de convocatoria \n \n" + "link: http://" + link + " \n \n Plataforma educativa - CBDMQ";
			
			emailService.enviarEmail(convocatoria.getCorreo(), EMAIL_SUBJECT_CONVOCATORIA, mensaje);
		}
		
		PeriodoAcademicoFor pa = new PeriodoAcademicoFor();

		pa.setConvocatoria(codConvocatoria);

		return pa;
	}

}
