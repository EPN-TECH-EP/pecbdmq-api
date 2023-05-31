package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Falta;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.FaltaService;

@Service
public class FaltaServiceImpl implements FaltaService{

	@Override
	public Falta save(Falta obj) throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Falta> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Falta> getById(int id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Falta update(Falta objActualizado) throws DataException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(int id) throws DataException {
		// TODO Auto-generated method stub
		
	}

}
