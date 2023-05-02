package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Aula;


public interface AulaRepository extends JpaRepository<Aula, Integer> {

	Optional<Aula> findByNombreIgnoreCase(String Nombre);
}
