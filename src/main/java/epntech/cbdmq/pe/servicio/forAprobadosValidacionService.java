package epntech.cbdmq.pe.servicio;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.DocumentException;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import jakarta.servlet.http.HttpServletResponse;

public interface forAprobadosValidacionService {

	
	void guardarDocumento(HttpServletResponse response, String nombre) throws IOException, ArchivoMuyGrandeExcepcion, DocumentException ;
	
}
