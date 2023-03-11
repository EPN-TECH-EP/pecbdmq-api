package epntech.cbdmq.pe.repositorio.admin;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.PeriodoAcademicoSemestreModulo;

public interface PeriodoAcademicoMSRepository extends JpaRepository<PeriodoAcademicoSemestreModulo, Integer> {

	@Query(value="select p.cod_periodo_academico as codigo, p.descripcion, p.estado, p.fecha_inicio_periodo_acad as fecha_inicio, p.fecha_fin_periodo_acad as fecha_fin, m.etiqueta as modulo, s.semestre  "
			+ "from cbdmq.gen_periodo_academico p, cbdmq.pro_semestre s, cbdmq.gen_modulo m "
			+ "where p.cod_modulo = m.cod_modulo "
			+ "and p.cod_semestre = s.cod_semestre "
			+ "and UPPER(m.estado) = 'ACTIVO' "
			+ "and UPPER(s.estado) = 'ACTIVO' "
			+ "and UPPER(p.estado) != 'ELIMINADO'", nativeQuery=true)
	List<PeriodoAcademicoSemestreModulo> getPeriodoAcademico();
	
}
