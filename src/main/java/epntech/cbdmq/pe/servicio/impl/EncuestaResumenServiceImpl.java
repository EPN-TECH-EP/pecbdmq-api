package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.EncuestaResumen;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.EncuestaResumenRepository;
import epntech.cbdmq.pe.servicio.EncuestaResumenService;

@Service
public class EncuestaResumenServiceImpl implements EncuestaResumenService {

	@Autowired
	private EncuestaResumenRepository encuestaresumenrepository;
	@Override
	public EncuestaResumen save(EncuestaResumen obj) throws DataException {
		// TODO Auto-generated method stub
		return encuestaresumenrepository.save(obj);
	}

	@Override
	public List<EncuestaResumen> getAll() {
		// TODO Auto-generated method stub
		return encuestaresumenrepository.findAll();
	}

	@Override
	public Optional<EncuestaResumen> getById(Integer codigo) {
		// TODO Auto-generated method stub
        return encuestaresumenrepository.findById(codigo);
	}

	@Override
	public EncuestaResumen update(EncuestaResumen objActualizado) throws DataException {
		// TODO Auto-generated method stub
		  return encuestaresumenrepository.save(objActualizado);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		encuestaresumenrepository.deleteById(id);
	}

}
