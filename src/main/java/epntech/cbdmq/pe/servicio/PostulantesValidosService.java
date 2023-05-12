package epntech.cbdmq.pe.servicio;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.Procedure;

import epntech.cbdmq.pe.dominio.util.PostulantesValidos;
import jakarta.mail.MessagingException;

public interface PostulantesValidosService {

	List<PostulantesValidos> getPostulantesValidos();
	
	void notificar(String mensaje, String prueba, Date fechaIni, Date fechaFin, LocalTime hora, Integer codPrueba) throws MessagingException, Exception, PSQLException;
	
	void onInitResultado(List<PostulantesValidos> obj, Integer prueba);
	
	Page<PostulantesValidos> getAllPaginado(Pageable pageable, Integer codPrueba) throws Exception;
	
	List<PostulantesValidos> getPostulantesAprovadosPrueba(Integer codPrueba);
}
