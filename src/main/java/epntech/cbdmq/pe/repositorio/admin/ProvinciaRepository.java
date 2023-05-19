package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.Provincia;
import epntech.cbdmq.pe.dominio.admin.ProvinciaProjection;


public interface ProvinciaRepository extends JpaRepository<Provincia, Integer> {

	Optional<Provincia> findByNombreIgnoreCase(String Nombre);
	
	@Query("SELECT p.codigo as codigo, p.nombre as nombre, p.estado as estado FROM Provincia p order by p.nombre") // Custom query to fetch only parent entities
    List<ProvinciaProjection> findAllParentEntities();
}
