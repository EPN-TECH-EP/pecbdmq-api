package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.NotificacionPrueba;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import jakarta.mail.MessagingException;

public interface NotificacionPruebaService {

	 NotificacionPrueba save(NotificacionPrueba obj) throws DataException, MessagingException;

	    List<NotificacionPrueba> getAll();

	    /*Optional<NotificacionPrueba> getById(Integer codigo);

	    NotificacionPrueba update(NotificacionPrueba objActualizado);

	    void delete(Integer codigo);*/
	
	
}
