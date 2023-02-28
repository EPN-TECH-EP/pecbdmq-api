package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.TipoProcedencia;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface TipoProcedenciaService {

	TipoProcedencia save(TipoProcedencia obj) throws DataException;
	
	List<TipoProcedencia> getAll();
	
	Optional<TipoProcedencia> getById(int codigo);
	
	TipoProcedencia update(TipoProcedencia objActualizado) throws DataException;
	
	void delete(int codigo) throws DataException;
}
