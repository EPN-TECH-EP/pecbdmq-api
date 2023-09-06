package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.dominio.admin.UsuarioEstudiante;
import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;
import epntech.cbdmq.pe.dominio.util.EstudianteDto;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface EstudianteService {
	
	Estudiante save(Estudiante obj);
	
	List<Estudiante> getAll();
	List<Estudiante> getAllWithOutParalelo();
	
	Optional<Estudiante> getById(int id);
	
	Estudiante update(Estudiante objActualizado);
	
	void delete(int id);
	
	Optional<Estudiante> getByIdEstudiante(String id);
	
	//Page<EstudianteDatos> getAllEstudiante(Pageable pageable) throws Exception;

	//List<EstudianteDatos> findAllEstudiante();
	
	void saveEstudiantes() throws DataException;
	
	Estudiante getEstudianteByUsuario(String codUsuario) throws DataException;
	UsuarioEstudiante getEstudianteByCodUsuario(Integer codUsuario);
	Estudiante getEstudianteByCodigoUnico(String codUnico);
	DatoPersonal getDatoPersonalByEstudiante(Integer Estudiante);
	List<EstudianteDto> getEstudiantesPA(List<Estudiante> estudiantes);
	List<EstudianteDto> getEstudiantesSinAsignarPA();
	List<EstudianteDto> getEstudiantesBaja();
	Estudiante getEstudianteByNotaFormacion(Integer codNotaFormacion);
	List<Estudiante> getEstudiantesIs(String Estado);


}
