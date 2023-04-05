package epntech.cbdmq.pe.repositorio.admin;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.PeriodoAcademicoSemestreModulo;

public interface PeriodoAcademicoMSRepository extends JpaRepository<PeriodoAcademicoSemestreModulo, Integer> {

	@Query(value="select p.cod_periodo_academico as codigo, p.descripcion, p.estado, "
			+ "p.fecha_inicio_periodo_acad as fecha_inicio, p.fecha_fin_periodo_acad as fecha_fin, "
			+ "c.nombre_catalogo_estados as estado_proceso "
			+ "from cbdmq.gen_periodo_academico p, cbdmq.gen_modulo_estados me, cbdmq.gen_catalogo_estados c "
			+ "where p.cod_modulo_estados = me.cod_modulo_estados "
			+ "and me.cod_catalogo_estados = c.cod_catalogo_estados "
			+ "and UPPER(me.estado) = 'ACTIVO' "
			+ "and upper(c.estado) = 'ACTIVO' "
			+ "and UPPER(p.estado) != 'ELIMINADO'", nativeQuery=true)
	List<PeriodoAcademicoSemestreModulo> getPeriodoAcademico();
	
}
