package epntech.cbdmq.pe.servicio;


import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface PruebaDetalleService {

	
Optional<PruebaDetalle> getBySubtipoAndPA(Integer subtipo, Integer periodo); 
	
	PruebaDetalle update(PruebaDetalle objActualizado);
	
	PruebaDetalle save(PruebaDetalle obj);
	
	

	
	List<PruebaDetalle>getAll();
	
	Optional<PruebaDetalle> getById(int id);
	
	

	void delete(int id) throws DataException;

}
