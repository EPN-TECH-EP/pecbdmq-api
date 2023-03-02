package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.admin.Paralelo;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.ParaleloRepository;
import epntech.cbdmq.pe.servicio.ParaleloService;

@Service
public class ParaleloServiceImpl implements ParaleloService{

	@Autowired
	private ParaleloRepository repo;
	
	@Override
	public Paralelo save(Paralelo obj) throws DataException{
		// TODO Auto-generated method stub
		if(obj.getNombreParalelo().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<Paralelo> objGuardado = repo.findBynombreParalelo(obj.getNombreParalelo());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}

		return repo.save(obj);
	}

	@Override
	public List<Paralelo> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Paralelo> getById(Integer codigo) {
		// TODO Auto-generated method stub
		return repo.findById(codigo);
	}

	@Override
	public Paralelo update(Paralelo objActualizado) throws DataException {
		// TODO Auto-generated method stub
		if(objActualizado.getNombreParalelo().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<Paralelo> objGuardado = repo.findBynombreParalelo(objActualizado.getNombreParalelo());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
		
		return repo.save(objActualizado);
	}

	@Override
	public void delete(Integer codigo) {
		// TODO Auto-generated method stub
		repo.deleteById(codigo);
	}
	
}
