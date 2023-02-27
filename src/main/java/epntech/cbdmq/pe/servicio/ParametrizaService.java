package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Parametrizacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface ParametrizaService  {

	Parametrizacion save(Parametrizacion obj) throws DataException;
	
	List<Parametrizacion>getAll();
	
	Optional<Parametrizacion> getbyId(Integer codigo);
	
	Parametrizacion update(Parametrizacion objActualizado) throws DataException;
	
	void delete(Integer codigo);

}
