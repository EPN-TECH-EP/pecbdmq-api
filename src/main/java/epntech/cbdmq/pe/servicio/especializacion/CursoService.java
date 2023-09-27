package epntech.cbdmq.pe.servicio.especializacion;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import epntech.cbdmq.pe.dominio.admin.CatalogoCurso;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.Requisito;
import epntech.cbdmq.pe.dominio.admin.especializacion.Curso;
import epntech.cbdmq.pe.dominio.admin.especializacion.CursoDocumento;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface CursoService {

	Curso save(String datos, List<MultipartFile> documentos/*, Long codTipoDocumento*/) throws JsonProcessingException, ParseException, MessagingException;

	Curso update(Curso objActualizado) throws MessagingException;

	List<Curso> listarAll();

	List<Curso> listarPorEstado(String estado);

	List<Curso> getByCodigoTipoCurso(Integer codigoTipoCurso);

	List<Curso> getByCodigoCatalogoCurso(Integer codigoCatalogoCurso);

	Curso getById(Long id);

	CursoDocumento updateEstadoAprobadoValidado(Boolean estadoAprobado, Boolean estadoValidado, String observaciones, Long codCursoEspecializacion, Long codDocumento);

	Curso iniciarCurso(Long codCursoEspecializacion);

	Curso updateEstadoProceso(Long estado, Long codCurso);

	Curso updateRequisitos(Long codCursoEspecializacion, List<Requisito> requisitos) ;

	Documento updateDocumento(Long codDocumento, MultipartFile archivo) throws IOException;

	Curso uploadDocumentos(Long codCursoEspecializacion, List<MultipartFile> archivos, Long codTipoDocumento)  throws IOException, ArchivoMuyGrandeExcepcion ;

	void delete(Long codCursoEspecializacion) ;

	Boolean cumpleMinimoAprobadosCurso(Long codCursoEspecializacion) ;

	void deleteDocumento(Long codCursoEspecializacion, Long codDocumento);

	List<Curso> listarPorEstadoAll(String estado);

	List<Curso> listarPorInstructorAndEstado(Integer codigoCursoInstructor, String estado);

	Curso updateEstado(long codigo, String estado);

    Curso updateEstadoAprobadoObservaciones(long codigo, Boolean aprobadoCurso, String Observaciones, long codigoUserAprueba) throws MessagingException;
	public Boolean reabrirCurso(Integer idCurso) throws DataException;
	List<Curso> listarPorEstudianteAndEstado(Integer codigoEstudiante, String estado);
	List<Curso> listarCerradosPorEstudiante(Integer codigoEstudiante);
	List<Curso> listarAllPorEstudiante(Integer codigoEstudiante);
	List<Curso> findByFechaInicioBetween(LocalDate startDate, LocalDate endDate);
}
