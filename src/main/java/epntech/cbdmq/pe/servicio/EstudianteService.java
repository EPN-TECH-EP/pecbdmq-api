package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.fichaPersonal.especializacion.EspecializacionEstudiante;
import epntech.cbdmq.pe.dominio.fichaPersonal.formacion.FormacionEstudiante;
import epntech.cbdmq.pe.dominio.fichaPersonal.profesionalizacion.ProfesionalizacionEstudiante;
import org.springframework.data.domain.Pageable;

import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;

public interface EstudianteService {
	
	Estudiante save(Estudiante obj);
	
	List<Estudiante> getAll();
	
	Optional<Estudiante> getById(int id);
	
	Estudiante update(Estudiante objActualizado);
	
	void delete(int id);
	
	Optional<Estudiante> getByIdEstudiante(String id);
	
	//Page<EstudianteDatos> getAllEstudiante(Pageable pageable) throws Exception;

	//List<EstudianteDatos> findAllEstudiante();
	
	void saveEstudiantes();
	Estudiante getEstudianteByUsuario(String codUsuario);

}
