package epntech.cbdmq.pe.servicio;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.InscripcionFor;
import epntech.cbdmq.pe.dominio.util.InscripcionResult;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import jakarta.mail.MessagingException;

public interface InscripcionForService {

	InscripcionResult insertarInscripcionConDocumentos(InscripcionFor inscripcion, List<MultipartFile> docsInscripcion) throws IOException, ArchivoMuyGrandeExcepcion, MessagingException, ParseException, DataException;
	
	Optional<InscripcionFor> getInscripcionById(Integer codigo);
	
	Optional<InscripcionFor> getById(int id);
	
	Boolean validaEdad(Date fecha);
}
