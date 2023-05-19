package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Canton;
import epntech.cbdmq.pe.dominio.admin.CantonProjection;
import epntech.cbdmq.pe.repositorio.admin.CantonRepository;
import epntech.cbdmq.pe.servicio.CantonService;

@Service
public class CantonServiceImpl implements CantonService {

	@Autowired
	private CantonRepository repo; 
	
	@Override
	public List<Canton> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Canton> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public List<CantonProjection> getAllByCodProvinciaId(int id) {
		// TODO Auto-generated method stub
		return repo.findAllByCodProvincia(id);
	}

}
