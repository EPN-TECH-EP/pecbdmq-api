package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.UnidadGestion;
import epntech.cbdmq.pe.repositorio.admin.UnidadGestionRepository;
import epntech.cbdmq.pe.servicio.UnidadGestionService;


@Service
public class UnidadGestionServiceImpl implements UnidadGestionService {

	@Autowired
	private UnidadGestionRepository repo;

	@Override
	public UnidadGestion saveUnidadGestion(UnidadGestion obj) {       
		return repo.save(obj);
	}

	@Override
	public List<UnidadGestion> getAllUnidadGestion() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<UnidadGestion> getUnidadGestionById(int codigo) {
		// TODO Auto-generated method stub
		return repo.findById(codigo);
	}

	@Override
	public UnidadGestion updateUnidadGestion(UnidadGestion objActualizado) {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}
}
