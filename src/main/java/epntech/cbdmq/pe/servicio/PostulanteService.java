package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import epntech.cbdmq.pe.dominio.admin.Postulante;
import epntech.cbdmq.pe.dominio.util.PostulanteDatos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface PostulanteService {

	Postulante save(Postulante obj, String proceso);
	
	List<Postulante> getAll();
	
	Optional<Postulante> getById(int id);
	
	void delete(int id) throws DataException;
	
	Postulante update(Postulante objActualizado) throws DataException; //PARA ASIGNAR UN POSTULANTE
	
	Postulante updateEstadoMuestra(Postulante objActualizado) throws DataException; //PARA ASIGNAR MUESTRA
	
	List<Postulante> getPostulantes(Integer usuario);
	
	List<Postulante> getPostulantesPaginado(Integer usuario, Pageable pageable);
	
	void updateState(Integer codpostulante);
	
	List<Postulante> getPostulantesAllPaginado(Pageable pageable);
	
	List<Postulante> getMuestra();
	
}
