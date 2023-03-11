package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Parroquia;



public interface ParroquiaRepository extends JpaRepository<Parroquia, Integer> {

	Optional<Parroquia> findByNombre(String Nombre);
	
	List<Parroquia> findAllByCodCanton(int codigo);
}
