package epntech.cbdmq.pe.repositorio.admin;

import epntech.cbdmq.pe.dominio.fichaPersonal.formacion.DatosEstudianteParaCrearUsuario;
import jakarta.persistence.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import epntech.cbdmq.pe.dominio.admin.EstudianteFor;

import java.util.Set;

public interface EstudianteForRepository extends JpaRepository<EstudianteFor, Integer> {

	@Procedure(value = "cbdmq.get_id")
	String getIdAspirante(String proceso);
	
	@Procedure(value = "cbdmq.insert_estudiantes")
	Integer insertEstudiantes();

	@Query(nativeQuery = true, name = "cbdmq.listar_estudiantes_formacion_generar_usuarios")
	Set<DatosEstudianteParaCrearUsuario> listaEstudiantesParaCrearUsuarios();
}

