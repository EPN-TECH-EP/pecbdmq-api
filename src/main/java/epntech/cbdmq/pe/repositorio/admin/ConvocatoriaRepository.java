package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Convocatoria;

public interface ConvocatoriaRepository extends JpaRepository<Convocatoria, Integer> {

	Optional<Convocatoria> findByNombre(String nombre);
}
