package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Semestre;

public interface SemestreRepository extends JpaRepository<Semestre, Integer> {

	Optional<Semestre> findBySemestreIgnoreCase(String nombre);
}
