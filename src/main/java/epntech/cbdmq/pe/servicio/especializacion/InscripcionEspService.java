package epntech.cbdmq.pe.servicio.especializacion;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.lowagie.text.DocumentException;
import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.dominio.util.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.especializacion.InscripcionDatosEsp;
import epntech.cbdmq.pe.dominio.admin.especializacion.InscripcionEsp;
import epntech.cbdmq.pe.dominio.admin.especializacion.ValidaRequisitos;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import jakarta.mail.MessagingException;

public interface InscripcionEspService {

	InscripcionEsp save(InscripcionEsp inscripcionEsp);
	InscripcionEsp saveFully(String inscripcionEsp,List<MultipartFile> archivos ) throws DataException, ArchivoMuyGrandeExcepcion, IOException;

	InscripcionEsp update(InscripcionEsp inscripcionEspActualizada);

	InscripcionEsp updateDelegado(Long codInscripcion, Long codigoUsuario);

	Optional<InscripcionDatosEsp> getById(Long codInscripcion);

	List<InscripcionDatosEspecializacion> getByCursoAndUsuarioPaginado(Long codCurso, Long codUsuario, Pageable pageable);

	List<InscripcionDatosEspecializacion> getAllByCursoPaginado(Long codCurso, Pageable pageable);

	void delete(Long codInscripcion);
	
	List<Documento> uploadFiles(Long codInscripcion, Long tipoDocumento, List<MultipartFile> archivos) throws DataException, IOException, ArchivoMuyGrandeExcepcion;
	
	void deleteDocumento(Long codInscripcion, Long codDocumento) throws DataException;
	
	void notificarInscripcion(Long codInscripcion) throws MessagingException, DataException;
	
	Boolean cumplePorcentajeMinimoInscritosCurso(long codCurso);
	
	List<InscripcionDatosEspecializacion> getByCurso(Long codCurso) throws DataException;
	Set<InscripcionDatosEspecializacion> getByCursoEstado(Long codCurso, String Estado) throws DataException;
	List<DatosInscripcionEsp> getAprobadosPruebas(Integer codCurso) throws DataException;
	List<DatosInscripcionEsp> getDesAprobadosPruebas(Integer codCurso) throws DataException;
	List<DatosInscripcionEsp> getAprobadosPruebasSubtipoPrueba(Integer codCurso, Integer codSubtipoPrueba);
	List<ValidaRequisitos> saveValidacionRequisito(List<ValidaRequisitos> validaRequisitos);

	List<ValidacionRequisitosDatos> getRequisitos(Long codInscripcion);
	
	void updateValidacionRequisito(List<ValidaRequisitos> validaRequisitos);
	
	List<InscritosEspecializacion> getInscritosValidosCurso(Long codCursoEspecializacion);
	List<InscripcionEsp> getAll2();

	void notificarPrueba(Long codCursoEspecializacion, Long codSubTipoPrueba);
	
	void notificarPruebaAprobada(Long codCursoEspecializacion, Long codSubTipoPrueba);
	DatoPersonalEstudianteDto confirmacionInscripcion(String Cedula, Long codCurso) throws Exception;
	DatoPersonalEstudianteDto colocarCorreoCiudadano(DatoPersonal datoPersonal) throws Exception;

}
