
package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;
import epntech.cbdmq.pe.dominio.util.PruebaDetalleDatos;
import epntech.cbdmq.pe.dominio.util.PruebaDetalleOrden;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface PruebaDetalleService {

	Optional<PruebaDetalle> getBySubtipoAndPA(Integer subtipo, Integer periodo);

	PruebaDetalle update(PruebaDetalle objActualizado) throws DataException;

	PruebaDetalle save(PruebaDetalle obj) throws DataException;

	List<PruebaDetalle> getAll();

	Optional<PruebaDetalle> getById(int id);

	void delete(int id) throws DataException;

	public List<PruebaDetalleDatos> listarTodosConDatosSubTipoPrueba();
	
	public Boolean reordenar(List<PruebaDetalleOrden> listaOrden) throws DataException;

}
