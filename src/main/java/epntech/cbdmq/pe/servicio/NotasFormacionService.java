package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import org.postgresql.util.PSQLException;

import epntech.cbdmq.pe.dominio.admin.NotasFormacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import jakarta.mail.MessagingException;

public interface NotasFormacionService {

	void saveAll(List<NotasFormacion> lista) throws DataException, MessagingException, PSQLException ;
	
	List<NotasFormacion> getByEstudiante(int id);
	
	NotasFormacion update(NotasFormacion objActualizado) throws DataException;
	
	Optional<NotasFormacion> getById(int id);
	
}

