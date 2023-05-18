package epntech.cbdmq.pe.servicio;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.util.DatoPersonalDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.DocumentoRuta;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import jakarta.mail.MessagingException;

public interface DatoPersonalService {

	DatoPersonal saveDatosPersonales(DatoPersonal obj) throws DataException, MessagingException;
	
	List<DatoPersonal> getAllDatosPersonales();
	DatoPersonalDto datosPersonalePersonalizado(String cedula);
	Page<DatoPersonal> getAllDatosPersonales(Pageable pageable) throws Exception;
	
	Optional<DatoPersonal> getDatosPersonalesById(Integer codigo);
	
	DatoPersonal updateDatosPersonales(DatoPersonal objActualizado) throws DataException;
	
	Page<DatoPersonal> search(String filtro, Pageable pageable) throws Exception;
	
	Documento guardarImagen(String proceso, Integer codigo, MultipartFile archivo) throws IOException, ArchivoMuyGrandeExcepcion;
	
	void deleteById(int id) throws DataException;
	
	
}
