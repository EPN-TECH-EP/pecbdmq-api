package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_RELACIONADOS;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.ParametrizaPruebaResumen;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

import epntech.cbdmq.pe.repositorio.admin.ParametrizaPruebaResumenRepository;
import epntech.cbdmq.pe.servicio.ParametrizaPruebaResumenService;

@Service
public class ParametrizaPruebaResumenServiceImpl implements ParametrizaPruebaResumenService {

	
	@Autowired
	private ParametrizaPruebaResumenRepository repo;
	
	
	@Override
	public ParametrizaPruebaResumen save(ParametrizaPruebaResumen obj) throws DataException {
		// TODO Auto-generated method stub
		return repo.save(obj);
	}

	@Override
	public List<ParametrizaPruebaResumen> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<ParametrizaPruebaResumen> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public ParametrizaPruebaResumen update(ParametrizaPruebaResumen objActualizado) throws DataException {
		// TODO Auto-generated method stub
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
