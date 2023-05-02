package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.TipoInstruccion;

public interface TipoInstruccionRepository extends JpaRepository<TipoInstruccion, Integer> {

	Optional<TipoInstruccion> findByTipoInstruccionIgnoreCase(String nombre);
}
