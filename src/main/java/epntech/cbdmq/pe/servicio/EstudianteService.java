package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.util.EspecializacionEstudiante;
import epntech.cbdmq.pe.dominio.util.FormacionEstudiante;
import epntech.cbdmq.pe.dominio.util.ProfesionalizacionEstudiante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import epntech.cbdmq.pe.dominio.admin.Estudiante;
import epntech.cbdmq.pe.dominio.util.EstudianteDatos;

public interface EstudianteService {
	
	Estudiante save(Estudiante obj);
	
	List<Estudiante> getAll();
	
	Optional<Estudiante> getById(int id);
	
	Estudiante update(Estudiante objActualizado);
	
	void delete(int id);
	
	Optional<Estudiante> getByIdEstudiante(String id);
	
	Page<EstudianteDatos> getAllEstudiante(Pageable pageable) throws Exception;

	List<EstudianteDatos> findAllEstudiante();
/*
	List<?> findPeriodosAcademicos(Integer id, Pageable pageable);

 */
	List<FormacionEstudiante> getHistoricos(String codEstudiante, Pageable pageable);
	List<EspecializacionEstudiante> getEspecializacionHistoricos();
	List<ProfesionalizacionEstudiante> getProfesionalizacionHistoricos();

}
