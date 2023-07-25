package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.FaltaPeriodo;
import epntech.cbdmq.pe.dominio.util.TipoFaltaPeriodoUtil;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface FaltaPeriodoService {
	
	FaltaPeriodo save(FaltaPeriodo obj) throws DataException;
	
	List<FaltaPeriodo> getAll();
	
	Optional<FaltaPeriodo> getById(int id);
	
	FaltaPeriodo update(FaltaPeriodo objActualizado) throws DataException;

	void delete(int id) throws DataException;
	List<TipoFaltaPeriodoUtil> getFaltasPeriodo();
	Integer insertarFaltaPeriodoFormacion();
}
