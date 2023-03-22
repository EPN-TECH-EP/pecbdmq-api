package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Prueba;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.PruebaRepository;
import epntech.cbdmq.pe.servicio.PruebaService;

@Service
public class PruebaServiceImpl implements PruebaService{

	@Autowired
	PruebaRepository repo;
	
	@Override
	public Prueba save(Prueba obj) throws DataException {
		
		if(obj.getPrueba().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<Prueba> objGuardado = repo.findByprueba(obj.getPrueba());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
        return repo.save(obj);
	}

	@Override
	public List<Prueba> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Prueba> getById(Integer codigo) {
		// TODO Auto-generated method stub
		return repo.findById(codigo);
	}

	@Override
	public Prueba update(Prueba objActualizado) {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(Integer codigo) {
		// TODO Auto-generated method stub
		repo.deleteById(codigo);
	}

}
