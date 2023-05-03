package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_RELACIONADOS;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.admin.TipoFuncionario;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.TipoFuncionarioRepository;
import epntech.cbdmq.pe.servicio.TipoFuncionarioService;

@Service
public class TipoFuncionarioServiceImpl implements TipoFuncionarioService {
	
	@Autowired
	private TipoFuncionarioRepository repo;

	@Override
	public TipoFuncionario save(TipoFuncionario obj) throws DataException {
		if(obj.getNombre().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<?> objGuardado = repo.findByNombreIgnoreCase(obj.getNombre());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
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
	public TipoFuncionario update(TipoFuncionario objActualizado)  throws DataException{
		
		Optional<TipoFuncionario> objGuardado = repo.findByNombreIgnoreCase(objActualizado.getNombre().toUpperCase());
		if (objGuardado.isPresent()&& !objGuardado.get().getCodigo().equals(objActualizado.getCodigo())) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
		
		return repo.save(objActualizado);
	}

	@Override
	public void delete(int id) throws DataException {
		Optional<?> objGuardado = repo.findById(id);
		if (objGuardado.isEmpty()) {
			throw new DataException(REGISTRO_NO_EXISTE);
		}
		try {
			repo.deleteById(id);
		} catch (Exception e) {
			if (e.getMessage().contains("constraint")) {
				throw new DataException(DATOS_RELACIONADOS);
			}
		}
	}

}
