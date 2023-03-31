package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Estados;

public interface EstadosService {

	Estados save(Estados obj);
	
	List<Estados> getAll();
	
	Optional<Estados> getById(int id);
	
	Estados update(Estados objActualizado);
	
	void delete(int id);
	
}
