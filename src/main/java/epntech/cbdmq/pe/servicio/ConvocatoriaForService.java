package epntech.cbdmq.pe.servicio;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.ConvocatoriaFor;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademicoFor;
import epntech.cbdmq.pe.dominio.admin.RequisitoFor;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import jakarta.mail.MessagingException;

public interface ConvocatoriaForService {	

	//ConvocatoriaFor insertarConvocatoriaConDocumentos(ConvocatoriaFor convocatoria, Set<DocumentoFor> documentos, Set<Requisito> requisito, Set<DocumentoRequisitoFor> documentosRequisito);
	
	PeriodoAcademicoFor insertarConvocatoriaConDocumentos(ConvocatoriaFor convocatoria, Set<RequisitoFor> requisito, 
			List<MultipartFile> docsPeriodoAcademico, List<MultipartFile> docsConvocatoria) throws IOException, ArchivoMuyGrandeExcepcion, MessagingException;

}
