package epntech.cbdmq.pe.servicio.especializacion;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import org.springframework.data.domain.Pageable;
import epntech.cbdmq.pe.dominio.util.DatoPersonalEstudianteDto;
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

	InscripcionEsp save(InscripcionEsp inscripcionEsp);
	
	InscripcionEsp update(InscripcionEsp inscripcionEspActualizada);

	InscripcionEsp updateDelegado(Long codInscripcion, Long codigoUsuario);

	Optional<InscripcionDatosEsp> getById(Long codInscripcion);
	
	List<InscripcionDatosEspecializacion> getAll();

	List<InscripcionDatosEspecializacion> getByUsuarioPaginado(Long codUsuario, Pageable pageable);

	List<InscripcionDatosEspecializacion> getAllPaginado(Pageable pageable);

	void delete(Long codInscripcion);
	
	List<Documento> uploadFiles(Long codInscripcion, Long tipoDocumento, List<MultipartFile> archivos) throws DataException, IOException, ArchivoMuyGrandeExcepcion;
	
	void deleteDocumento(Long codInscripcion, Long codDocumento) throws DataException;
	
	void notificarInscripcion(Long codInscripcion) throws MessagingException, DataException;
	
	Boolean cumplePorcentajeMinimoInscritosCurso(long codCurso);
	
	List<InscripcionDatosEspecializacion> getByCurso(Long codCurso) throws DataException;
	
	List<ValidaRequisitos> saveValidacionRequisito(List<ValidaRequisitos> validaRequisitos);

	List<ValidacionRequisitosDatos> getRequisitos(Long codInscripcion);
	
	void updateValidacionRequisito(List<ValidaRequisitos> validaRequisitos);
	
	List<InscritosEspecializacion> getInscritosValidosCurso(Long codCursoEspecializacion);
	
	void notificarPrueba(Long codCursoEspecializacion, Long codSubTipoPrueba);
	
	void notificarPruebaAprobada(Long codCursoEspecializacion, Long codSubTipoPrueba);
	DatoPersonalEstudianteDto confirmacionInscripcion(String Cedula) throws Exception;
	DatoPersonalEstudianteDto colocarCorreoCiudadano(DatoPersonal datoPersonal) throws Exception;
}
