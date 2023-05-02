package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.TipoDocumento;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface TipoDocumentoService {

	TipoDocumento save(TipoDocumento obj) throws DataException;
	
	List<TipoDocumento> getAll();
	
	Optional<TipoDocumento> getById(int id);
	
	TipoDocumento update(TipoDocumento objActualizado) throws DataException;
	
	void delete(int id) throws DataException;
}
