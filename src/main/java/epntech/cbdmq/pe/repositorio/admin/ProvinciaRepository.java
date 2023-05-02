package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Provincia;


public interface ProvinciaRepository extends JpaRepository<Provincia, Integer> {

	Optional<Provincia> findByNombreIgnoreCase(String Nombre);
}
