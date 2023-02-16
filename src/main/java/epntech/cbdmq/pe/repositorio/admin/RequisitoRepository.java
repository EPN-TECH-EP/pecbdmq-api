package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Requisito;

public interface RequisitoRepository extends JpaRepository<Requisito, Integer> {

	Optional<Requisito> findByNombre(String nombre);
}
