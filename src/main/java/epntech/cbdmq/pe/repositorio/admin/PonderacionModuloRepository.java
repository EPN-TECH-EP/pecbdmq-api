package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import epntech.cbdmq.pe.dominio.admin.PonderacionModulos;

public interface PonderacionModuloRepository extends JpaRepository<PonderacionModulos, Integer> {

	@Query(value="select\r\n"
			+ "	      gp.porcentaje_final_ponderacion as porcentaje,\r\n"
			+ "       gcn.tipo_componente_nota as tiponota, \r\n"
			+ "       gm.descripcion as modulo, \r\n"
			+ "       gm.etiqueta as etiqueta,\r\n"
			+ "       gpa.descripcion as periodo,\r\n"
			+ "       gpa.estado as estado,\r\n"
			+ "       gpa.fecha_inicio_periodo_acad as fechainicio,\r\n"
			+ "       gpa.fecha_fin_periodo_acad as fechafin      \r\n"
			+ "from cbdmq.gen_ponderacion gp,\r\n"
			+ "	  cbdmq.gen_componente_nota gcn,\r\n"
			+ "	  cbdmq.gen_modulo gm,\r\n"
			+ "	  cbdmq.gen_periodo_academico gpa \r\n"
			+ "where gcn.cod_componente_nota = gp.cod_componente_nota \r\n"
			+ "	  and gm.cod_modulo =gp.cod_modulo\r\n"
			+ "	  and gpa.cod_periodo_academico =gp.cod_periodo_academico\r\n"
			+ "	  and UPPER(gp.estado) != 'ELIMINADO'\r\n"
			+ "	  and upper(gm.estado) ='ACTIVO'\r\n"
			+ "	  and upper(gpa.estado) ='ACTIVO'\r\n"
			+ "	  and upper(gcn.estado) = 'ACTIVO'", nativeQuery=true)
	List<PonderacionModulos> getPonderacionModulos();
	
	
	
}
