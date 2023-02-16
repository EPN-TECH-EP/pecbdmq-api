package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.TipoInstruccion;
import epntech.cbdmq.pe.repositorio.admin.TipoInstruccionRepository;
import epntech.cbdmq.pe.servicio.TipoInstruccionService;


@Service
public class TipoInstruccionServiceImpl implements TipoInstruccionService {

	@Autowired
	private TipoInstruccionRepository repo;
	
	@Override
	public TipoInstruccion save(TipoInstruccion obj) {
		// TODO Auto-generated method stub
		return repo.save(obj);
	}

	@Override
	public List<TipoInstruccion> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<TipoInstruccion> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public TipoInstruccion update(TipoInstruccion objActualizado) {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}

}
