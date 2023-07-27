package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.formacion.NotaEstudianteFormacionDto;
import epntech.cbdmq.pe.dominio.admin.formacion.NotaMateriaByEstudiante;
import org.postgresql.util.PSQLException;

import epntech.cbdmq.pe.dominio.admin.NotasFormacion;
import epntech.cbdmq.pe.dominio.util.NotasDatosFormacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import jakarta.mail.MessagingException;

public interface NotasFormacionService {

	void saveAll(List<NotasFormacion> lista) throws DataException, MessagingException, PSQLException ;
	
	List<NotasFormacion> getByEstudiante(int id);
	
	NotasFormacion update(NotasFormacion objActualizado) throws DataException;
	NotasFormacion updateII(NotasFormacion objActualizado) throws DataException;
	
	Optional<NotasFormacion> getById(int id);
	
	List<NotasDatosFormacion> getNotasEstudiante(long codEstudiante);
	
	List<NotasDatosFormacion> getNotasMateria(long codMateria);
	NotaEstudianteFormacionDto getEstudianteMateriaParalelo(Integer codMateria);
	void insertarEstudiantesNotas();
	List<NotaMateriaByEstudiante> getNotaMateriasByEstudiante(Integer codEstudiante,String tipoInstructor);
	List<NotaMateriaByEstudiante> getNotaMateriasWithCoordinadorByEstudiante(Integer codEstudiante);
	public NotaMateriaByEstudiante getMateriaWithCoordinadorByEstudianteNotaFormacion(Integer codEstudiante,Integer codNotaFormacion);
}

