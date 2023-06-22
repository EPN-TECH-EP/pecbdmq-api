package epntech.cbdmq.pe.servicio.especializacion;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.especializacion.InscripcionEsp;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface InscripcionEspService {

	InscripcionEsp save(InscripcionEsp inscripcionEsp) throws DataException;
	
	InscripcionEsp update(InscripcionEsp inscripcionEspActualizada) throws DataException;
	
	Optional<InscripcionEsp> getById(Long codInscripcion) throws DataException;
	
	List<InscripcionEsp> getAll();
	
	void delete(Long codInscripcion)  throws DataException;
	
	InscripcionEsp uploadFiles(Long codInscripcion, List<MultipartFile> archivos) throws DataException;
	
}
