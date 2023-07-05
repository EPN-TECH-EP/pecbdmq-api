package epntech.cbdmq.pe.repositorio;

import epntech.cbdmq.pe.dominio.admin.EjeMateria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EjeMateriaRepository extends JpaRepository<EjeMateria,Long> {
    Optional<EjeMateria> findByNombreEjeMateriaIgnoreCase(String nombreEjeMateria);
}
