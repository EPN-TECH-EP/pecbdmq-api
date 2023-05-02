package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.TipoProcedencia;


public interface TipoProcedenciaRepository extends JpaRepository<TipoProcedencia, Integer> {
	Optional<TipoProcedencia> findByNombreIgnoreCase(String nombre);

}
