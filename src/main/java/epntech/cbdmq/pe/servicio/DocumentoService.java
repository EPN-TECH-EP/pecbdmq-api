package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Documento;

public interface DocumentoService {

	Documento save(Documento obj);
	
	List<Documento> listAll();
	
	Optional<Documento> getById(int id);
	
	Documento update(Documento objActualizado);
	
	void delete(int id);
}
