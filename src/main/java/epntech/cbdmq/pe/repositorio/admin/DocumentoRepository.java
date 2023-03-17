package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, Integer> {

	Optional<Documento> findByNombre(String nombre);
}
