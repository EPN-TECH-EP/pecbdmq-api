package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Requisito;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface RequisitoService {

	Requisito save(Requisito obj) throws DataException;
	
	List<Requisito> getAll();
	List<Requisito> getAllOrder();

	Optional<Requisito> getById(int id);
	
	Requisito update(Requisito objActualizado) throws DataException;
	
	void delete(int id) throws DataException;
	
}
