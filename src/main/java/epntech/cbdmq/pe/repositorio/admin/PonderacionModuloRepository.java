package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import epntech.cbdmq.pe.dominio.admin.PonderacionModulos;

public interface PonderacionModuloRepository extends JpaRepository<PonderacionModulos, Integer> {

	@Query(value="select \r\n"
			+ "gp.cod_ponderacion ,\r\n"
			+ "gm.cod_modulo as cod_modulo, \r\n"
			+ "gpa.cod_periodo_academico  as cod_periodo_academico, \r\n"
			+ "gcn.cod_componente_nota as cod_componente_nota, \r\n"
			+ "gtn.cod_tipo_nota as cod_tipo_nota, \r\n"
			+ "gp.porcentaje_final_ponderacion as porcentajefinalponderacion,\r\n"
			+ "gp.porcentaje_nota_materia as porcentajenotamateria,\r\n"
			+ "gp.fecha_inicio_vigencia as fechainiciovigencia , \r\n"
			+ "gp.fecha_fin_vigencia as fechafinvigencia ,\r\n"
			+ "gp.estado as estado ,\r\n"
			+ "gm.etiqueta as modulo_desc, \r\n"
			+ "gpa.descripcion as periodo_academico_desc, \r\n"
			+ "gcn.tipo_componente_nota as componente_nota_desc, \r\n"
			+ "gtn.tipo_nota as tipo_nota_desc\r\n"
			+ "from cbdmq.gen_ponderacion gp, \r\n"
			+ "cbdmq.gen_modulo gm, \r\n"
			+ "cbdmq.gen_periodo_academico gpa,\r\n"
			+ "cbdmq.gen_componente_nota gcn,\r\n"
			+ "cbdmq.gen_tipo_nota gtn \r\n"
			+ "where gp.cod_modulo = gm.cod_modulo \r\n"
			+ "and gp.cod_periodo_academico = gpa.cod_periodo_academico \r\n"
			+ "and gp.cod_componente_nota = gcn.cod_componente_nota \r\n"
			+ "and gp.cod_tipo_nota =gtn.cod_tipo_nota  \r\n"
			+ "and UPPER(gp.estado) != 'ELIMINADO'\r\n"
			+ "and upper(gm.estado) ='ACTIVO'\r\n"
			+ "and upper(gpa.estado) ='ACTIVO'\r\n"
			+ "and upper(gcn.estado) = 'ACTIVO'\r\n"
			+ "and upper(gtn.estado) ='ACTIVO'", nativeQuery=true)
	List<PonderacionModulos> getPonderacionModulos();
	
	
	
}
