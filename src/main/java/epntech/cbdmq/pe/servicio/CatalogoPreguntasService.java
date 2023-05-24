package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;


import epntech.cbdmq.pe.dominio.admin.CatalogoPreguntas;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface CatalogoPreguntasService {

	CatalogoPreguntas save(CatalogoPreguntas obj) throws DataException;
	
	List<CatalogoPreguntas> getAll();
	
	Optional<CatalogoPreguntas> getById(int id);
	
	CatalogoPreguntas update(CatalogoPreguntas objActualizado) throws DataException;

	void delete(int id) throws DataException;
	
	
}
