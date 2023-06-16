package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Parametrizacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.ParametrizacionRepository;
import epntech.cbdmq.pe.servicio.ParametrizaService;

@Service
public class ParametrizacionServiceImpl implements ParametrizaService {

	@Autowired
	ParametrizacionRepository repo;
	
	@Override
	public Parametrizacion save(Parametrizacion obj) throws DataException {
		if(obj.getObservacionParametriza().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<Parametrizacion> objGuardado = repo.findByObservacionParametriza(obj.getObservacionParametriza());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
		return repo.save(obj);
	}

	@Override
	public List<Parametrizacion> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Parametrizacion> getbyId(Integer codigo) {
		// TODO Auto-generated method stub
		return repo.findById(codigo);
	}

	@Override
	public Parametrizacion update(Parametrizacion objActualizado) throws DataException {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(Integer codigo) {
		// TODO Auto-generated method stub
		repo.deleteById(codigo);
	}
	

}
