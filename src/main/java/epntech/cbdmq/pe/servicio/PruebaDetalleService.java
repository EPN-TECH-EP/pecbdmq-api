package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;
import epntech.cbdmq.pe.dominio.util.PruebaDetalleDatos;
import epntech.cbdmq.pe.dominio.util.PruebaDetalleOrden;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface PruebaDetalleService {

	Optional<PruebaDetalle> getBySubtipoAndPA(Integer subtipo, Integer periodo);

	Optional<PruebaDetalle> findByCodCursoEspecializacionAndCodSubtipoPrueba(Integer codCursoEspecializacion, Integer subtipo);

	PruebaDetalle update(PruebaDetalle objActualizado);

	PruebaDetalle save(PruebaDetalle obj);

	List<PruebaDetalle> getAll();

	PruebaDetalle getById(int id);

	void delete(int id) throws DataException;

	public List<PruebaDetalleDatos> listarTodosConDatosSubTipoPrueba();

    List<PruebaDetalleDatos> listarTodosConDatosSubTipoPrueba(Integer codCurso);

    public String getTipoResultado(int codSubtipoPrueba);

	public Boolean reordenar(List<PruebaDetalleOrden> listaOrden) throws DataException;


}
