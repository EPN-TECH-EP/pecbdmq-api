package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import epntech.cbdmq.pe.dominio.admin.Canton;
import epntech.cbdmq.pe.dominio.admin.CantonProjection;




public interface CantonRepository extends JpaRepository<Canton, Integer> {

	Optional<Canton> findByNombre(String Nombre);
	
	@Query("select c.codigo as codigo, c.nombre as nombre, c.estado as estado, c.codProvincia as codigoProvincia from Canton c where c.codProvincia = :codigoProvincia order by c.nombre")
	List<CantonProjection> findAllByCodProvincia(@Param("codigoProvincia") int codigoProvincia);
	
	
}
