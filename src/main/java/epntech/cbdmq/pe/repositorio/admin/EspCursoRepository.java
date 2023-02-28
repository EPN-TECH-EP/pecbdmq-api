package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.EspCurso;
import epntech.cbdmq.pe.dominio.admin.Materia;

public interface EspCursoRepository  extends JpaRepository<EspCurso, Integer> {

	Optional<EspCurso> findBynombrecursoespecializacion(String nombre);
	
}
