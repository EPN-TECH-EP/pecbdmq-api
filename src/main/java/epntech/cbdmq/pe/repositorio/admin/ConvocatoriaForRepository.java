package epntech.cbdmq.pe.repositorio.admin;

import java.time.LocalTime;
import java.util.Date;
import java.util.Set;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import epntech.cbdmq.pe.dominio.admin.ConvocatoriaFor;
import epntech.cbdmq.pe.dominio.admin.DocumentoFor;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademico;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@Transactional
public class ConvocatoriaForRepository {

	
	//@Procedure(value = "cbdmq.get_id")
	//String getId(String proceso);
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public void insertarConvocatoriaConDocumentos(Integer periodo, Integer modulo,  String nombre, String estado, Date fechaInicio, Date fechaFin, LocalTime horaInicio, LocalTime horaFin, String codigoUnico, Integer cupoHombres, Integer cupoMujeres,  Set<DocumentoFor> documentos) {
        String sqlConvocatoria = "INSERT INTO cbdmq.gen_convocatoria (cod_periodo_academico, cod_modulo, nombre_convocaria, estado, fecha_inicio_convocatoria, fecha_fin_convocatoria, hora_inicio_convocatoria, hora_fin_convocatoria, codigo_unico_convocatoria, cupo_hombres, cupo_mujeres) " 
        						+"VALUES (:periodo, :modulo, :nombre, :estado, :fechaInicio, :fechaFin, :horaInicio, :horaFin, :codigoUnico, :cupoHombres, :cupoMujeres)";
        String sqlDocumento = "INSERT INTO cbdmq.gen_documento (autorizacion, cod_tipo_documento, descripcion, estado_validacion, codigo_unico_documento, nombre_documento, observaciones, ruta, estado) " 
        					+"VALUES (:autorizacion, :tipo, :descripcion, :estadoValidacion, :codigoUnico, :nombre, :observaciones, :ruta, :estado)";
        String sqlConvocatoriaDocumento = "INSERT INTO cbdmq.gen_convocatoria_documento (cod_convocatoria, cod_documento) " +
                               			"VALUES (:cod_convocatoria, :cod_documento)";
        
        ConvocatoriaFor convocatoria = new ConvocatoriaFor();
        convocatoria.setCodPeriodoAcademico(periodo);
        convocatoria.setCodModulo(modulo);
        convocatoria.setNombre(nombre);
        convocatoria.setEstado(estado);
        convocatoria.setFechaInicioConvocatoria(fechaInicio);
        convocatoria.setFechaFinConvocatoria(fechaFin);
        convocatoria.setHoraInicioConvocatoria(horaInicio);
        convocatoria.setHoraFinConvocatoria(horaFin);
        convocatoria.setCodigoUnico(codigoUnico);
        convocatoria.setCupoHombres(cupoHombres);
        convocatoria.setCupoMujeres(cupoMujeres);
        convocatoria.setDocumentos(documentos);
        
        entityManager.createNativeQuery(sqlConvocatoria)
                     .setParameter("periodo", convocatoria.getCodPeriodoAcademico())
                     .setParameter("modulo", convocatoria.getCodModulo())
                     .setParameter("nombre", convocatoria.getNombre())
                     .setParameter("estado", convocatoria.getEstado())
                     .setParameter("fechaInicio", convocatoria.getFechaInicioConvocatoria())
                     .setParameter("fechaFin", convocatoria.getFechaFinConvocatoria())
                     .setParameter("horaInicio", convocatoria.getHoraInicioConvocatoria())
                     .setParameter("horaFin", convocatoria.getHoraFinConvocatoria())
                     .setParameter("codigoUnico", convocatoria.getCodigoUnico())
                     .setParameter("cupoHombres", convocatoria.getCupoHombres())
                     .setParameter("cupoMujeres", convocatoria.getCupoMujeres());

        
        for (DocumentoFor documento : convocatoria.getDocumentos()) {
        	
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

        entityManager.persist(convocatoria);
        
        for (DocumentoFor documento : convocatoria.getDocumentos()) {
            entityManager.createNativeQuery(sqlConvocatoriaDocumento)
                         .setParameter("cod_convocatoria", convocatoria.getCodConvocatoria())
                         .setParameter("cod_documento", documento.getCodigoDocumento());
        }
        
        insertarPeriodoAcademico();
    }
	
	public void insertarPeriodoAcademico() {
		String estado = "ACTIVO";
		String descripcion = "formacion";
		
		String sqlQuery = "INSERT INTO cbdmq.gen_periodo_academico(estado, descripcion) " 
        						+"VALUES (:estado, :descripcion)";
       
        PeriodoAcademico periodo = new PeriodoAcademico();
        periodo.setEstado(estado);
        periodo.setDescripcion(descripcion);
        
        entityManager.createNativeQuery(sqlQuery)
                     .setParameter("estado", periodo.getEstado())
                     .setParameter("descripcion", periodo.getDescripcion());

        entityManager.persist(periodo);
        
    }
}
