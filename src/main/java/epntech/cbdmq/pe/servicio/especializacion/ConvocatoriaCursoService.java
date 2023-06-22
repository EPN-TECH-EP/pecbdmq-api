package epntech.cbdmq.pe.servicio.especializacion;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.especializacion.ConvocatoriaCurso;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import jakarta.mail.MessagingException;

public interface ConvocatoriaCursoService {

	ConvocatoriaCurso save(ConvocatoriaCurso convocatoriaCurso, List<MultipartFile> archivos) throws DataException, IOException, ArchivoMuyGrandeExcepcion;
	
	List<ConvocatoriaCurso> listAll();
	
	Optional<ConvocatoriaCurso> getByID(Long codConvocatoria)  throws DataException ;
	
	Optional<ConvocatoriaCurso> getByCurso(Long codCursoEspecializacion)  throws DataException ;
	
	void delete(Long codConvocatoria) throws DataException;
	
	ConvocatoriaCurso update(ConvocatoriaCurso convocatoriaCursoActualizado) throws DataException;
	
	void deleteDocumento(Long codConvocatoria, Long codDocumento) throws DataException;
	
	void notificar(String mensaje, Long codConvocatoria) throws MessagingException, DataException;
	
	Boolean validaConvocatoriaCursoActiva(Long codConvocatoria);
}
