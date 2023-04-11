package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Estados;

public interface EstadosRepository extends JpaRepository<Estados, Integer> {

	Optional<Estados> findByNombre(String nombre);
}
