package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Materia;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface MateriaService {

	Materia save(Materia obj) throws DataException;
	
	List<Materia> getAll();
	
	Optional<Materia> getById(int id);
	
	Materia update(Materia objActualizado) throws DataException;
	
	void delete(int id) throws DataException;
}
