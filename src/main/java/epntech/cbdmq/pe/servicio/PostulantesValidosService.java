package epntech.cbdmq.pe.servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import epntech.cbdmq.pe.dominio.util.PostulantesValidos;
import jakarta.mail.MessagingException;

public interface PostulantesValidosService {

	List<PostulantesValidos> getPostulantesValidos();
	
	void notificar(String mensaje) throws MessagingException;
	
	void onInitResultado(List<PostulantesValidos> obj, Integer modulo, Integer prueba);
	
	Page<PostulantesValidos> getAllPaginado(Pageable pageable) throws Exception;
	
	
}
