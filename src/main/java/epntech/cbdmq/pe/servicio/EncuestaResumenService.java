package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.EncuestaResumen;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface EncuestaResumenService {

	
	EncuestaResumen save(EncuestaResumen obj) throws DataException;
	
	List<EncuestaResumen> getAll();
	
	Optional<EncuestaResumen> getById(Integer codigo);
	
	EncuestaResumen update(EncuestaResumen objActualizado)throws DataException;
	
	void delete(int id);
	
}
