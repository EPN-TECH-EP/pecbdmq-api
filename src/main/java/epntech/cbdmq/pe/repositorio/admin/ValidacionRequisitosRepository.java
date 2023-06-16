package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.util.ValidacionRequisitosLista;

public interface ValidacionRequisitosRepository extends JpaRepository<ValidacionRequisitosLista, Integer> {
	
	
	@Query(value = "select vr.cod_validacion_requisitos as cod_validacion, vr.cod_requisitos, r.nombre_requisito, vr.estado, vr.observaciones, vr.cod_postulante, vr.estado_muestra, vr.observacion_muestra  "
			+ "from cbdmq.gen_convocatoria c, cbdmq.gen_convocatoria_requisito cr, cbdmq.gen_requisito r, "
			+ "cbdmq.gen_periodo_academico pa, cbdmq.gen_validacion_requisitos vr "
			+ "where c.cod_convocatoria = cr.cod_convocatoria "
			+ "and cr.cod_requisito = r.cod_requisito "
			+ "and r.cod_requisito = vr.cod_requisitos "
			+ "and c.cod_periodo_academico = pa.cod_periodo_academico "
			+ "and upper(r.estado) = 'ACTIVO' "
			+ "and upper(c.estado) = 'ACTIVO' "
			+ "and upper(pa.estado) = 'ACTIVO' "
			+ "and vr.cod_postulante = :codPostulante", nativeQuery=true)
	List<ValidacionRequisitosLista> getRequisitos(Integer codPostulante);

}
