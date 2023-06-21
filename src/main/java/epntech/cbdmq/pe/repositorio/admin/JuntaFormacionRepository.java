package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.JuntaFormacion;

public interface JuntaFormacionRepository extends JpaRepository<JuntaFormacion, Long> {

	List<JuntaFormacion> findByCodPeriodoAcademico(Long codPeriodoAcademico);
}
