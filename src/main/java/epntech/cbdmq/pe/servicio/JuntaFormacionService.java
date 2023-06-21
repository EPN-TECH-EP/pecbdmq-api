package epntech.cbdmq.pe.servicio;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.JuntaFormacion;
import epntech.cbdmq.pe.dominio.util.DatosFile;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface JuntaFormacionService {

	List<DatosFile> save(List<MultipartFile> archivos,  Long codTipoDocumento) throws DataException, IOException, ArchivoMuyGrandeExcepcion;
	
	void delete(Long codJunta) throws DataException;
	
	List<JuntaFormacion> getDocumentos();
}
