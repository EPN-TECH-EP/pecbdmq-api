package epntech.cbdmq.pe.servicio;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.lowagie.text.DocumentException;
import epntech.cbdmq.pe.dominio.admin.ResultadoPrueba;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import jakarta.servlet.http.HttpServletResponse;

public interface ResultadoPruebaService {

	
	ResultadoPrueba save(ResultadoPrueba obj) throws DataException;
	
	List<ResultadoPrueba>getAll();
	
	Optional<ResultadoPrueba> getById(Integer codigo);
	
	ResultadoPrueba update(ResultadoPrueba objActualizado) throws DataException;
	
	void delete(Integer codigo);
	Boolean generarArchivoAprobados(HttpServletResponse response, Integer codSubtipoPrueba, Integer codCurso) throws DataException, DocumentException, IOException;
	
	
	
}
