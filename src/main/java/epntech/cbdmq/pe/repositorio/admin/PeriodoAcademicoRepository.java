package epntech.cbdmq.pe.repositorio.admin;


import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.PeriodoAcademico;

public interface PeriodoAcademicoRepository extends JpaRepository<PeriodoAcademico, Integer> {

	Optional<PeriodoAcademico> findByDescripcion(String descripcion);
	
	Optional<PeriodoAcademico> findByFechaInicioAndFechaFin(Date fechaIni, Date fechaFin);
	
}
