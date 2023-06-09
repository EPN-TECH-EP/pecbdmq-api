package epntech.cbdmq.pe.servicio.especializacion;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.especializacion.Cronograma;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface CronogramaService {

	Cronograma save(MultipartFile archivo, Long codTipoDocumento) throws IOException, ArchivoMuyGrandeExcepcion ;
	
	Cronograma update(Long codCronograma, MultipartFile archivo, String estado)  throws IOException, ArchivoMuyGrandeExcepcion, DataException ;
	
	Optional<Cronograma> getById(Long id);
	
	List<Cronograma> listAll();
	
	void delete(Long id);
}
