package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;


import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface AulaService {
	
	Aula save(Aula obj) throws DataException;
	
	List<Aula> getAll();
	
	Optional<Aula> getById(int id);
	
	Aula update(Aula objActualizado) throws DataException;

	void delete(int id) throws DataException;
}
