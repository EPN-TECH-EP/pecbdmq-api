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
	
	List<DatoPersonal> findAllByCorreoPersonal(String correoPersonal);
	
	Page<DatoPersonal> findByNombreContainingOrApellidoContaining(String nombre, String apellido, Pageable pageable);
	
	@Query(value = "SELECT d FROM gen_dato_personal d WHERE d.nombre LIKE %:filtro% OR d.apellido LIKE %:filtro%")
    Page<DatoPersonal> search(@Param("filtro") String filtro, Pageable pageable);
	
	@Query(
            value = "SELECT d FROM gen_dato_personal d WHERE d.nombre LIKE %:filtro% OR d.apellido LIKE %:filtro%"
    )
    Page<DatoPersonal> searchNativo(@Param("filtro") String filtro, Pageable pageable);
	@Query("select gd from Estudiante ge\n" +
			"join gen_dato_personal gd\n" +
			"on ge.codDatosPersonales = gd.codDatosPersonales\n" +
			"where ge.codEstudiante= :codEstudiante")
	DatoPersonal getDatoPersonalByEstudiante(@Param("codEstudiante") Integer codEstudiante);
	List<DatoPersonal> findByCodEstacionIsNotNullOrCodUnidadGestionIsNotNullOrCorreoInstitucionalIsNotNullOrCodCargoIsNotNullOrCodRangoIsNotNullOrCodGradoIsNotNull();
	/*@EntityGraph(attributePaths = "documentos")
	List<DatoPersonal> findAll();*/
	
	
}
