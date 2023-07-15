package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Paralelo;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface ParaleloService {

	Paralelo save(Paralelo obj) throws DataException;
	
	List<Paralelo> getAll();
	
	Optional<Paralelo> getById(Integer codigo);
	
	Paralelo update(Paralelo objActualizado) throws DataException;

	void delete(Integer codigo);
	List<Paralelo> getParalelosPA();
	
}
