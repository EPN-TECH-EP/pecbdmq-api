package epntech.cbdmq.pe.servicio;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.InscripcionFor;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import jakarta.mail.MessagingException;

public interface InscripcionForService {

	Integer insertarInscripcionConDocumentos(InscripcionFor inscripcion, List<MultipartFile> docsInscripcion) throws IOException, ArchivoMuyGrandeExcepcion, MessagingException;
	
	Optional<InscripcionFor> getInscripcionById(Integer codigo);
}
