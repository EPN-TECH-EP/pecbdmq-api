package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Semestre;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface SemestreService {

	Semestre save(Semestre obj) throws DataException;
	
	List<Semestre> getAll();
	
	Optional<Semestre> getById(int id);
	
	Semestre update(Semestre obActualizado) throws DataException ;
	
	void deleteById(int id) throws DataException;
}
