package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.admin.CatalogoCurso;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface CatalogoCursoService {

	CatalogoCurso save(CatalogoCurso obj) throws DataException;
	
	List<CatalogoCurso> getAll();
	
	Optional<CatalogoCurso> getById(int id);
	
	CatalogoCurso update(CatalogoCurso objActualizado) throws DataException;

	void delete(int id) throws DataException;
}
	
	

