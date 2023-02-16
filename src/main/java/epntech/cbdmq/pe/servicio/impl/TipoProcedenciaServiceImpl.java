package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.TipoProcedencia;
import epntech.cbdmq.pe.repositorio.admin.TipoProcedenciaRepository;
import epntech.cbdmq.pe.servicio.TipoProcedenciaService;

@Service
public class TipoProcedenciaServiceImpl implements TipoProcedenciaService {

	@Autowired
	TipoProcedenciaRepository repo;
	
	@Override
	public TipoProcedencia save(TipoProcedencia obj) {	
		return repo.save(obj);
	}

	@Override
	public List<TipoProcedencia> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<TipoProcedencia> getById(int codigo) {
		// TODO Auto-generated method stub
		return repo.findById(codigo);
	}

	@Override
	public TipoProcedencia update(TipoProcedencia objActualizado) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TipoProcedencia delete(int codigo) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
