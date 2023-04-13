package epntech.cbdmq.pe.repositorio.admin;


import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.PeriodoAcademico;
import epntech.cbdmq.pe.dominio.admin.PostulanteDatoPersonal;

public interface PeriodoAcademicoRepository extends JpaRepository<PeriodoAcademico, Integer> {

	Optional<PeriodoAcademico> findByDescripcion(String descripcion);
	
	Optional<PeriodoAcademico> findByFechaInicioAndFechaFin(Date fechaIni, Date fechaFin);
	
	@Query(value = "SELECT e.nombre_catalogo_estados "
				+ "FROM cbdmq.gen_catalogo_estados e, cbdmq.gen_modulo_estados me, cbdmq.gen_modulo m, cbdmq.gen_periodo_academico pa "
				+ "WHERE e.cod_catalogo_estados = me.cod_catalogo_estados "
				+ "AND me.cod_modulo = m.cod_modulo "
				+ "AND me.cod_modulo_estados = pa.cod_modulo_estados "
				+ "AND UPPER(e.estado) = 'ACTIVO' "
				+ "AND UPPER(me.estado) = 'ACTIVO' "
				+ "AND UPPER(m.estado) = 'ACTIVO' "
				+ "AND UPPER(pa.estado) = 'ACTIVO' "
				+ "AND UPPER(trim(m.etiqueta)) = 'FORMACIÓN' ", nativeQuery = true)
	String getEstado();
	
}
