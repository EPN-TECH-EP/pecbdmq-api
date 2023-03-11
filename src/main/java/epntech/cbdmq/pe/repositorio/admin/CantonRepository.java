package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Canton;




public interface CantonRepository extends JpaRepository<Canton, Integer> {

	Optional<Canton> findByNombre(String Nombre);
	
	List<Canton> findAllByCodProvincia(int codigo);
}
