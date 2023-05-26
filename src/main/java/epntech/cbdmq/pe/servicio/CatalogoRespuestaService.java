package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.admin.Baja;
import epntech.cbdmq.pe.dominio.admin.CatalogoRespuesta;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface CatalogoRespuestaService  {

	CatalogoRespuesta save(CatalogoRespuesta obj) throws DataException;

    List<CatalogoRespuesta> getAll();

    Optional<CatalogoRespuesta> getById(Integer codigo);

    CatalogoRespuesta update(CatalogoRespuesta objActualizado)throws DataException;

    void delete(Integer codigo);
	
}
