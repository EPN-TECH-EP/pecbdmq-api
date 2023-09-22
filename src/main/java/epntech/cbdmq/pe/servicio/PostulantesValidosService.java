package epntech.cbdmq.pe.servicio;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import com.lowagie.text.DocumentException;
import epntech.cbdmq.pe.dominio.util.PostulantesAprobadosReprobados;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import jakarta.servlet.http.HttpServletResponse;
import org.postgresql.util.PSQLException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.Procedure;

import epntech.cbdmq.pe.dominio.util.PostulantesValidos;
import jakarta.mail.MessagingException;

public interface PostulantesValidosService {
	
	List<PostulantesValidos> getPostulantesValidos();
	List<PostulantesValidos> getPostulantesValidosDiferentBaja();

	List<PostulantesValidos> getAllPostulantesValidos();
	List<PostulantesValidos> getAllPostulantesNoValidos();
	List<PostulantesAprobadosReprobados> getPospulantesAprobadosReprobados();
	
	Page<PostulantesValidos> getAllPostulantesValidosPaginado(Pageable pageable);
	
	// OrderApellido
	List<PostulantesValidos> getAllPostulantesValidosOrderApellido();
	
	Page<PostulantesValidos> getAllPostulantesValidosPaginadoOrderApellido(Pageable pageable);
	
	void notificar(String mensaje, String prueba, Date fechaIni, Date fechaFin, LocalTime hora, Integer codPrueba) throws MessagingException, Exception, PSQLException;
	
	void onInitResultado(List<PostulantesValidos> obj, Integer prueba);
	
	Page<PostulantesValidos> getAllPaginado(Pageable pageable, Integer codPrueba) throws Exception;
	
	List<PostulantesValidos> getPostulantesAprovadosPrueba(Integer codPrueba);

	// buscar postulantes válidos por filtro
	List<PostulantesValidos> getPostulantesValidosFiltro(String tipoFiltro, String valorFiltro);

	/////////////////////////////////////////////////////////
	// listado postulantes para seguimiento de inscripciones
	List<PostulantesValidos> getPostulantesTodoFiltro(String tipoFiltro, String valorFiltro);

	Page<PostulantesValidos> getAllPostulantesTodoPaginado(Pageable pageable);

	Page<PostulantesValidos> getAllPostulantesTodoPaginadoOrderApellido(Pageable pageable);

	public Boolean generarArchivos(HttpServletResponse response, Boolean esAprobado) throws DataException, DocumentException, IOException;
	public Boolean generarArchivosAprobados(HttpServletResponse response) throws DataException, DocumentException, IOException;
	public Boolean generarArchivosReprobados(HttpServletResponse response) throws DataException, DocumentException, IOException;


}

