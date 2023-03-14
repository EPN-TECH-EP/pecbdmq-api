package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import jakarta.mail.MessagingException;

public interface DatoPersonalService {

	DatoPersonal saveDatosPersonales(DatoPersonal obj) throws DataException, MessagingException;
	
	List<DatoPersonal> getAllDatosPersonales();
	
	Page<DatoPersonal> getAllDatosPersonales(Pageable pageable) throws Exception;
	
	Optional<DatoPersonal> getDatosPersonalesById(Integer codigo);
	
	DatoPersonal updateDatosPersonales(DatoPersonal objActualizado) throws DataException;
	
	Page<DatoPersonal> search(String filtro, Pageable pageable) throws Exception;
	
	void deleteById(int id) throws DataException;
	
	
}