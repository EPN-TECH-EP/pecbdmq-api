package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.DocumentoHabilitante;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface DocumentoHabilitanteService {

	DocumentoHabilitante save(DocumentoHabilitante obj) throws DataException;
	
	List<DocumentoHabilitante> getAll();
	
	Optional<DocumentoHabilitante> getById(int id);
	
	DocumentoHabilitante update(DocumentoHabilitante objActualizado) throws DataException;
	
	void delete(int id) throws DataException;
}
