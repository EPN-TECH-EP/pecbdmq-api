package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import epntech.cbdmq.pe.dominio.admin.DatoPersonal;


public interface DatoPersonalRepository extends JpaRepository<DatoPersonal, Integer> {

	//@Value("${spring.jpa.properties.hibernate.default_schema}")
	
	Optional<DatoPersonal> findOneByCedula(String Cedula);
	
	Optional<DatoPersonal> findOneByCorreo(String correo);
	
	Page<DatoPersonal> findByNombreContainingOrApellidoContaining(String nombre, String apellido, Pageable pageable);
	
	@Query(value = "SELECT d FROM gen_dato_personal d WHERE d.nombre LIKE %:filtro% OR d.apellido LIKE %:filtro%")
    Page<DatoPersonal> search(@Param("filtro") String filtro, Pageable pageable);
	
	@Query(
            value = "SELECT d FROM gen_dato_personal d WHERE d.nombre LIKE %:filtro% OR d.apellido LIKE %:filtro%"
    )
    Page<DatoPersonal> searchNativo(@Param("filtro") String filtro, Pageable pageable);
	
	
}
