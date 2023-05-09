package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.util.DatoPersonalDto;
import epntech.cbdmq.pe.dominio.util.UsuarioDtoRead;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import epntech.cbdmq.pe.dominio.admin.DatoPersonal;


public interface DatoPersonalRepository extends JpaRepository<DatoPersonal, Integer> {

	//@Value("${spring.jpa.properties.hibernate.default_schema}")
	
	Optional<DatoPersonal> findOneByCedula(String Cedula);
	
	Page<DatoPersonal> findByNombreContainingOrApellidoContaining(String nombre, String apellido, Pageable pageable);
	
	@Query(value = "SELECT d FROM gen_dato_personal d WHERE d.nombre LIKE %:filtro% OR d.apellido LIKE %:filtro%")
    Page<DatoPersonal> search(@Param("filtro") String filtro, Pageable pageable);
	
	@Query(
            value = "SELECT d FROM gen_dato_personal d WHERE d.nombre LIKE %:filtro% OR d.apellido LIKE %:filtro%"
    )
    Page<DatoPersonal> searchNativo(@Param("filtro") String filtro, Pageable pageable);
	
	/*@EntityGraph(attributePaths = "documentos")
	List<DatoPersonal> findAll();*/

	/*
	@Query(nativeQuery = true, name = "DatoPersonalDto.informacionDetallada")
	List<DatoPersonalDto> buscarInformaciónPersonalizada(@Param("cedula") String cedula,Pageable pageable);
	 */
	@Query(nativeQuery = true, name = "DatoPersonalDto.informacionDetallada")
	List<DatoPersonalDto> buscarInformaciónPersonalizada();

}
