package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.TipoFuncionario;
import epntech.cbdmq.pe.repositorio.admin.TipoFuncionarioRepository;
import epntech.cbdmq.pe.servicio.TipoFuncionarioService;

@Service
public class TipoFuncionarioServiceImpl implements TipoFuncionarioService {
	
	@Autowired
	private TipoFuncionarioRepository repo;

	@Override
	public TipoFuncionario save(TipoFuncionario obj) {
		// TODO Auto-generated method stub
		return repo.save(obj);
	}

	@Override
	public List<TipoFuncionario> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<TipoFuncionario> getById(Integer codigo) {
		// TODO Auto-generated method stub
		return repo.findById(codigo);
	}

	@Override
	public TipoFuncionario update(TipoFuncionario objActualizado) {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}

}
