package epntech.cbdmq.pe.servicio;

import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;

public interface PruebaDetalleService {

	Optional<PruebaDetalle> getBySubtipoAndPA(Integer subtipo, Integer periodo); 
	
	PruebaDetalle update(PruebaDetalle objActualizado);
	
	PruebaDetalle save(PruebaDetalle obj);
}
