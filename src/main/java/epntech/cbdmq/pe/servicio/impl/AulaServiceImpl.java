package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.repositorio.admin.AulaRepository;
import epntech.cbdmq.pe.servicio.AulaService;
import epntech.cbdmq.pe.dominio.admin.Aula;

@Service
public class AulaServiceImpl implements AulaService{

	@Autowired
	private AulaRepository repo;
	
	@Override
	public Aula save(Aula obj){
		// TODO Auto-generated method stub
		return repo.save(obj);
	}

	@Override
	public List<Aula> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Aula> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Aula update(Aula objActualizado) {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}

}
