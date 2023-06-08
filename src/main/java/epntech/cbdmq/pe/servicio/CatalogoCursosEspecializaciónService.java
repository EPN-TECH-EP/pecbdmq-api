package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.CatalogoCursosEspecialización;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface CatalogoCursosEspecializaciónService {

	CatalogoCursosEspecialización save(CatalogoCursosEspecialización obj) throws DataException;

    List<CatalogoCursosEspecialización> getAll();

    Optional<CatalogoCursosEspecialización> getById(Integer codigo);

    CatalogoCursosEspecialización update(CatalogoCursosEspecialización objActualizado)throws DataException;

    void delete(Integer codigo);
	
	
}
