package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Semestre;
import epntech.cbdmq.pe.repositorio.admin.SemestreRepository;
import epntech.cbdmq.pe.servicio.SemestreService;

@Service
public class SemestreServiceImpl implements SemestreService {

	@Autowired
	private SemestreRepository repo;

	@Override
	public Semestre save(Semestre obj) {
		// TODO Auto-generated method stub
		return repo.save(obj);	
	}

	@Override
	public List<Semestre> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Semestre> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Semestre update(Semestre objActualizado) {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}
}
