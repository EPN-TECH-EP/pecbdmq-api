package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Parroquia;
import epntech.cbdmq.pe.repositorio.admin.ParroquiaRepository;
import epntech.cbdmq.pe.servicio.ParroquiaService;

@Service
public class ParroquiaServiceImpl implements ParroquiaService {

	@Autowired
	private ParroquiaRepository repo; 
	
	@Override
	public List<Parroquia> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Parroquia> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public List<Parroquia> getAllByCodCantonId(int id) {
		// TODO Auto-generated method stub
		return repo.findAllByCodCanton(id);
	}

}
