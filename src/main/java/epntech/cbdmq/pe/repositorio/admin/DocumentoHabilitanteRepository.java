package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.DocumentoHabilitante;

public interface DocumentoHabilitanteRepository extends JpaRepository<DocumentoHabilitante, Integer> {

	Optional<DocumentoHabilitante> findByNombre(String nombre);
}
