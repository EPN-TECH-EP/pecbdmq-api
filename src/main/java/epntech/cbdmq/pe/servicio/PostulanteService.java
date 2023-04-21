package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Postulante;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface PostulanteService {

	Postulante save(Postulante obj, String proceso);
	
	List<Postulante> getAll();
	
	Optional<Postulante> getById(int id);
	
	void delete(int id) throws DataException;
	
	Postulante update(Postulante objActualizado) throws DataException;
	
	List<Postulante> getPostulantes(Integer usuario);
}
