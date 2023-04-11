package epntech.cbdmq.pe.repositorio.admin;

import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_MUY_GRANDE;

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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.ConvocatoriaFor;
import epntech.cbdmq.pe.dominio.admin.DatosFile;
import epntech.cbdmq.pe.dominio.admin.DocumentoFor;
import epntech.cbdmq.pe.dominio.admin.DocumentoRequisitoFor;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademico;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademicoFor;
import epntech.cbdmq.pe.dominio.admin.Requisito;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;

@Repository
@Transactional
public class ConvocatoriaForRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;

	@Value("${spring.servlet.multipart.max-file-size}")	
	public DataSize TAMAÑO_MÁXIMO;

	public PeriodoAcademicoFor insertarConvocatoriaConDocumentos(ConvocatoriaFor convocatoria, Set<Requisito> requisitos, List<MultipartFile> docsPeriodoAcademico, List<MultipartFile> docsConvocatoria) throws IOException, ArchivoMuyGrandeExcepcion {
		String sqlConvocatoria = "INSERT INTO cbdmq.gen_convocatoria (cod_periodo_academico, nombre_convocatoria, estado, fecha_inicio_convocatoria, fecha_fin_convocatoria, hora_inicio_convocatoria, hora_fin_convocatoria, codigo_unico_convocatoria, cupo_hombres, cupo_mujeres, correo) "
				+ "VALUES (:periodo, :nombre, :estado, :fechaInicio, :fechaFin, :horaInicio, :horaFin, :codigoUnico, :cupoHombres, :cupoMujeres, :correo)";
		String sqlDocumento = "INSERT INTO cbdmq.gen_documento (autorizacion, cod_tipo_documento, descripcion, estado_validacion, codigo_unico_documento, nombre_documento, observaciones, ruta, estado) "
				+ "VALUES (:autorizacion, :tipo, :descripcion, :estadoValidacion, :codigoUnico, :nombre, :observaciones, :ruta, :estado)";
		String sqlConvocatoriaDocumento = "INSERT INTO cbdmq.gen_convocatoria_documento (cod_convocatoria, cod_documento) "
				+ "VALUES (:cod_convocatoria, :cod_documento)";
		String sqlPeriodoAcademicoDocumento = "INSERT INTO cbdmq.gen_periodo_academico_documento (cod_periodo_academico, cod_documento) "
				+ "VALUES (:cod_periodo_academico, :cod_documento)";
		String sqlConvocatoriaRequisito = "INSERT INTO cbdmq.gen_convocatoria_requisito (cod_convocatoria, cod_requisito) "
				+ "VALUES (:cod_convocatoria, :cod_requisito)";
		
		/*String sqlRequisito = "INSERT INTO cbdmq.gen_requisito(cod_convocatoria, cod_funcionario, descripcion_requisito, estado, nombre_requisito, es_documento) "
				+ "VALUES (:cod_convocatoria, :funcionario, :descripcion, :estado, :nombre, :esDocumento)";*/
		/*String sqlRequisitoDocumento = "INSERT INTO cbdmq.gen_requisito_documento (cod_requisito, cod_documento) "
				+ "VALUES (:codRequisito, :codDocumento)";*/

		//PERIODO ACADEMICO
		String sqlQuery = "INSERT INTO cbdmq.gen_periodo_academico(estado, descripcion) "
				+ "VALUES (:estado, :descripcion)";

		List<DatosFile>lista= new ArrayList<>();
		
		PeriodoAcademico periodo = new PeriodoAcademico();
		periodo.setEstado("ACTIVO");
		periodo.setDescripcion("FORMACION");

		entityManager.createNativeQuery(sqlQuery).setParameter("estado", periodo.getEstado())
				.setParameter("descripcion", periodo.getDescripcion());

		entityManager.persist(periodo);
		
		
		//CONVOCATORIA
		//convocatoria.setDocumentos(docsConvocatoria);

		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("cbdmq.get_id");
		query.registerStoredProcedureParameter("proceso", String.class, ParameterMode.IN);
		query.setParameter("proceso", "C");
		query.execute();
		Object resultado = query.getSingleResult();
		convocatoria.setCodigoUnico(resultado.toString());
		
		convocatoria.setCodPeriodoAcademico(periodo.getCodigo());
		
		entityManager.createNativeQuery(sqlConvocatoria)
			.setParameter("periodo", convocatoria.getCodPeriodoAcademico())
			.setParameter("nombre", convocatoria.getNombre())
			.setParameter("estado", convocatoria.getEstado())
			.setParameter("fechaInicio", convocatoria.getFechaInicioConvocatoria())
			.setParameter("fechaFin", convocatoria.getFechaFinConvocatoria())
			.setParameter("horaInicio", convocatoria.getHoraInicioConvocatoria())
			.setParameter("horaFin", convocatoria.getHoraFinConvocatoria())
			.setParameter("codigoUnico", convocatoria.getCodigoUnico())
			.setParameter("cupoHombres", convocatoria.getCupoHombres())
			.setParameter("cupoMujeres", convocatoria.getCupoMujeres())
			.setParameter("correo", convocatoria.getCorreo());

		//guardamos archivos de la convocatoria en el servidor
		
		lista = guardarArchivo(docsConvocatoria, "Convocatoria", convocatoria.getCodConvocatoria().toString());
		Set<DocumentoFor> dConvocatoria = new HashSet<>();
		for (DatosFile datosFile : lista) {
			DocumentoFor dd = new DocumentoFor();
			dd.setEstado("ACTIVO");
			dd.setNombre(datosFile.getNombre());
			dd.setRuta(datosFile.getRuta());
			dConvocatoria.add(dd);
		}
		
		//convocatoria.setDocumentos(dConvocatoria);
		for (DocumentoFor documento : dConvocatoria) {

			entityManager.createNativeQuery(sqlDocumento)
				.setParameter("autorizacion", documento.getAutorizacion())
				.setParameter("tipo", documento.getTipo())
				.setParameter("descripcion", documento.getDescripcion())
				.setParameter("estadoValidacion", documento.getEstadoValidacion())
				.setParameter("codigoUnico", documento.getCodigoUnico())
				.setParameter("nombre", documento.getNombre())
				.setParameter("observaciones", documento.getObservaciones())
				.setParameter("ruta", documento.getRuta()).setParameter("estado", documento.getEstado());
			entityManager.persist(documento);
		}

		Integer codConvocatoria = convocatoria.getCodConvocatoria();
		
		//for (DocumentoFor documento : convocatoria.getDocumentos()) {
		for (DocumentoFor documento : dConvocatoria) {
			entityManager.createNativeQuery(sqlConvocatoriaDocumento)
					.setParameter("cod_convocatoria", codConvocatoria)
					.setParameter("cod_documento", documento.getCodigoDocumento());
		}

		entityManager.persist(convocatoria);
		
		
		//guardamos archivos del Periodo Academico en el servidor
		
		lista = guardarArchivo(docsPeriodoAcademico, "Periodo Academico", periodo.getCodigo().toString());
		Set<DocumentoFor> dPeriodoAcademico = new HashSet<>();
		for (DatosFile datosFile : lista) {
			DocumentoFor dd = new DocumentoFor();
			dd.setEstado("ACTIVO");
			dd.setNombre(datosFile.getNombre());
			dd.setRuta(datosFile.getRuta());
			dPeriodoAcademico.add(dd);
		}
		
		for (DocumentoFor documento : dPeriodoAcademico) {

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
		}
		
		for (DocumentoFor documento : dPeriodoAcademico) {
			entityManager.createNativeQuery(sqlPeriodoAcademicoDocumento)
					.setParameter("cod_periodo_academico", periodo.getCodigo())
					.setParameter("cod_documento", documento.getCodigoDocumento());
		}

		//entityManager.persist(convocatoria);
		
		
		//REQUISITOS
		
		for (Requisito elemento : requisitos) {
			entityManager.createNativeQuery(sqlConvocatoriaRequisito)
					.setParameter("cod_convocatoria", codConvocatoria)
					.setParameter("cod_requisito", elemento.getCodigoRequisito());
		}
		
		/*for (Requisito requisito : convocatoria.getRequisitos()) {

			entityManager.createNativeQuery(sqlRequisito)
				.setParameter("cod_convocatoria", codConvocatoria)
				.setParameter("funcionario", requisito.getCodFuncionario())
				.setParameter("descripcion", requisito.getDescripcion())
				.setParameter("nombre", requisito.getNombre())
				.setParameter("estado", requisito.getEstado())
				.setParameter("esDocumento", requisito.getEsDocumento());
			entityManager.persist(requisito);
		}*/
		
		/*Iterator<Requisito> req = requisitos.iterator();
		while (req.hasNext()) {
			Requisito elemento = req.next();
			for (DocumentoRequisitoFor documento : elemento.getDocumentosRequisito()) {
	
				entityManager.createNativeQuery(sqlDocumento)
					.setParameter("autorizacion", documento.getAutorizacion())
					.setParameter("tipo", documento.getTipo())
					.setParameter("descripcion", documento.getDescripcion())
					.setParameter("estadoValidacion", documento.getEstadoValidacion())
					.setParameter("codigoUnico", documento.getCodigoUnico())
					.setParameter("nombre", documento.getNombre())
					.setParameter("observaciones", documento.getObservaciones())
					.setParameter("ruta", documento.getRuta()).setParameter("estado", documento.getEstado());
				entityManager.persist(documento);
			}
			entityManager.createNativeQuery(sqlRequisitoDocumento)
			.setParameter("codRequisito", elemento.getCodigoRequisito())
			.setParameter("codDocumento", elemento.getDocumentosRequisito());
		}*/
		

		PeriodoAcademicoFor pa = new PeriodoAcademicoFor();
		
		pa.setConvocatoria(codConvocatoria);
		pa.setPeriodoAcademico(periodo.getCodigo());
		
		
		return pa;
	}
	
	public List<DatosFile> guardarArchivo(List<MultipartFile> archivo, String id, String proceso)
			throws IOException, ArchivoMuyGrandeExcepcion {
		String resultado;

		resultado = ruta(proceso, id);
		Path ruta = Paths.get(resultado).toAbsolutePath().normalize();

		if (!Files.exists(ruta)) {
			Files.createDirectories(ruta);
		}

		List<DatosFile> lista = new ArrayList<>();
		DatosFile documentos = new DatosFile();
		// Files.copy(archivo.getInputStream(),
		// ruta.resolve(archivo.getOriginalFilename()),
		// StandardCopyOption.REPLACE_EXISTING);
		for (Iterator iterator = archivo.iterator(); iterator.hasNext();) {
			MultipartFile multipartFile = (MultipartFile) iterator.next();
			if (multipartFile.getSize() > TAMAÑO_MÁXIMO.toBytes()) {
				throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
			}

			Files.copy(multipartFile.getInputStream(), ruta.resolve(multipartFile.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			//LOGGER.info("Archivo guardado: " + resultado + multipartFile.getOriginalFilename());
			documentos.setRuta(resultado + multipartFile.getOriginalFilename());
			documentos.setNombre(multipartFile.getOriginalFilename());
			lista.add(documentos);

		}

		return lista;
	}

	private String ruta(String proceso, String id) {

		String resultado = null;
		resultado = ARCHIVOS_RUTA + proceso + "/" + id + "/";
		return resultado;
	}

}
