package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.util.*;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import epntech.cbdmq.pe.dominio.admin.Estudiante;

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

	List<FormacionEstudiante> getHistoricos(String codEstudiante, Pageable pageable);
	List<EspecializacionEstudiante> getEspecializacionHistoricos(String codEstudiante, Pageable pageable);
	List<ProfesionalizacionEstudiante> getProfesionalizacionHistoricos(String codEstudiante, Pageable pageable);
	Estudiante getEstudianteByUsuario(String codUsuario);

}
