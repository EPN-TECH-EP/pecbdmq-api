package epntech.cbdmq.pe.repositorio.admin;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import epntech.cbdmq.pe.dominio.admin.PeriodoEvaluacion;

public interface PeriodoEvaluacionRepository extends JpaRepository<PeriodoEvaluacion, Integer> {

	Optional<PeriodoEvaluacion> findByDescripcion(String descripcion);
	
	Optional<PeriodoEvaluacion> findByFechaInicioAndFechaFin(Date fechaIni, Date fechaFin);
}
