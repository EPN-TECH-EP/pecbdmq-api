package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.EncuestaResultado;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface EncuestaResultadoService {

	
	EncuestaResultado save(EncuestaResultado obj) throws DataException;
	
	List<EncuestaResultado> getAll();
	
	Optional<EncuestaResultado> getById(int id);
	
	EncuestaResultado update(EncuestaResultado objActualizado) throws DataException;

	void delete(int id) throws DataException;
}
