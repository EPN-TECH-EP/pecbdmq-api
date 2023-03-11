package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Postulante;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.PostulanteRepository;
import epntech.cbdmq.pe.servicio.PostulanteService;

@Service
public class PostulanteServiceimpl implements PostulanteService {

	@Autowired
	private PostulanteRepository repo;
	
	@Override
	public Postulante save(Postulante obj, String proceso) {
		// TODO Auto-generated method stub
		obj.setIdPostulante(repo.getIdPostulante(proceso));
		return repo.save(obj);
	}

	@Override
	public List<Postulante> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Postulante> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public void delete(int id) throws DataException {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}

	
}
