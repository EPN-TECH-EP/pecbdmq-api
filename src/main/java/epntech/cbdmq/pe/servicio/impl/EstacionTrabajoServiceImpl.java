package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.EstacionTrabajo;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.EstacionTrabajoRepository;
import epntech.cbdmq.pe.servicio.EstacionTrabajoService;

@Service
public class EstacionTrabajoServiceImpl implements EstacionTrabajoService {

	@Autowired
	private EstacionTrabajoRepository repo;
	
	@Override
	public EstacionTrabajo save(EstacionTrabajo obj) throws DataException {
		// TODO Auto-generated method stub
		return repo.save(obj);
	}

	@Override
	public List<EstacionTrabajo> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<EstacionTrabajo> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public EstacionTrabajo update(EstacionTrabajo objActualizado) throws DataException {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(int id) throws DataException {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}

}
