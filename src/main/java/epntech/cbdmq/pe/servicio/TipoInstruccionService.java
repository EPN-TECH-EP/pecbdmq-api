package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.TipoInstruccion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface TipoInstruccionService {

	TipoInstruccion save(TipoInstruccion obj) throws DataException;
	
	List<TipoInstruccion> getAll();
	
	Optional<TipoInstruccion> getById(int id);
	
	TipoInstruccion update(TipoInstruccion objActualizado) throws DataException;
	
	void delete(int id) throws DataException;
}
