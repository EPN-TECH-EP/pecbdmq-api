package epntech.cbdmq.pe.servicio.especializacion;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.especializacion.InscripcionEsp;
import epntech.cbdmq.pe.dominio.util.InscripcionDatosEspecializacion;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface InscripcionEspService {

	InscripcionEsp save(InscripcionEsp inscripcionEsp) throws DataException;
	
	InscripcionEsp update(InscripcionEsp inscripcionEspActualizada) throws DataException;
	
	Optional<InscripcionDatosEspecializacion> getById(Long codInscripcion) throws DataException;
	
	List<InscripcionDatosEspecializacion> getAll();
	
	void delete(Long codInscripcion)  throws DataException;
	
	List<Documento> uploadFiles(Long codInscripcion, Long tipoDocumento, List<MultipartFile> archivos) throws DataException, IOException, ArchivoMuyGrandeExcepcion;
	
	void deleteDocumento(Long codInscripcion, Long codDocumento) throws DataException;
}
