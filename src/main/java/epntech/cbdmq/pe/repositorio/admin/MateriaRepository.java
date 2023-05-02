package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Materia;

public interface MateriaRepository extends JpaRepository<Materia, Integer> {

	Optional<Materia> findByNombreIgnoreCase(String nombre);
}
