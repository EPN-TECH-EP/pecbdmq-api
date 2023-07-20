package epntech.cbdmq.pe.servicio.especializacion;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.especializacion.InscripcionDatosEsp;
import epntech.cbdmq.pe.dominio.admin.especializacion.InscripcionEsp;
import epntech.cbdmq.pe.dominio.admin.especializacion.ValidaRequisitos;
import epntech.cbdmq.pe.dominio.util.InscripcionDatosEspecializacion;
import epntech.cbdmq.pe.dominio.util.InscritosEspecializacion;
import epntech.cbdmq.pe.dominio.util.ValidacionRequisitosDatos;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import jakarta.mail.MessagingException;

public interface InscripcionEspService {

	InscripcionEsp save(InscripcionEsp inscripcionEsp) throws DataException;
	
	InscripcionEsp update(InscripcionEsp inscripcionEspActualizada) throws DataException;
	
	Optional<InscripcionDatosEsp> getById(Long codInscripcion) throws DataException;
	
	List<InscripcionDatosEspecializacion> getAll();
	
	void delete(Long codInscripcion)  throws DataException;
	
	List<Documento> uploadFiles(Long codInscripcion, Long tipoDocumento, List<MultipartFile> archivos) throws DataException, IOException, ArchivoMuyGrandeExcepcion;
	
	void deleteDocumento(Long codInscripcion, Long codDocumento) throws DataException;
	
	void notificarInscripcion(Long codInscripcion) throws MessagingException, DataException;
	
	Boolean cumplePorcentajeMinimoInscritosCurso(long codCurso);
	
	List<InscripcionDatosEspecializacion> getByCurso(Long codCurso) throws DataException;
	
	List<ValidaRequisitos> saveValidacionRequisito(List<ValidaRequisitos> validaRequisitos)  throws MessagingException, DataException;
	
	List<ValidacionRequisitosDatos> getValidacionRequisito(Long codEstudiante, Long codCursoEspecializacion);
	
	List<ValidaRequisitos> updateValidacionRequisito(List<ValidaRequisitos> validaRequisitos)  throws MessagingException, DataException;
	
	List<InscritosEspecializacion> getInscritosValidosCurso(Long codCursoEspecializacion);
	
	void notificarPrueba(Long codCursoEspecializacion, Long codSubTipoPrueba) throws MessagingException, DataException;
	
	void notificarPruebaAprobada(Long codCursoEspecializacion, Long codSubTipoPrueba) throws MessagingException, DataException;
}
