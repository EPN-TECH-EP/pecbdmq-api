package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.DocumentoHabilitante;

public interface DocumentoHabilitanteService {

	DocumentoHabilitante save(DocumentoHabilitante obj);
	
	List<DocumentoHabilitante> getAll();
	
	Optional<DocumentoHabilitante> getById(int id);
	
	DocumentoHabilitante update(DocumentoHabilitante objActualizado);
	
	void delete(int id);
}
