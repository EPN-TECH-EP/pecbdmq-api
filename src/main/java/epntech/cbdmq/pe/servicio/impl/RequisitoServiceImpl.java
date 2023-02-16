package epntech.cbdmq.pe.servicio.impl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Requisito;
import epntech.cbdmq.pe.repositorio.admin.RequisitoRepository;
import epntech.cbdmq.pe.servicio.RequisitoService;

@Service
public class RequisitoServiceImpl implements RequisitoService {

	@Autowired
	private RequisitoRepository repo;
	
	@Override
	public Requisito save(Requisito obj) {
		// TODO Auto-generated method stub
		return repo.save(obj);
	}

	@Override
	public List<Requisito> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Requisito> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Requisito update(Requisito objActualizado) {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}

}
