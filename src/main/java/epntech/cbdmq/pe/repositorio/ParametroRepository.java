package epntech.cbdmq.pe.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.Parametro;

public interface ParametroRepository extends JpaRepository<Parametro, Long> {

	Optional<Parametro> findByNombreParametro(String nombre);
}
