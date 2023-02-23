package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.repositorio.admin.AulaRepository;
import epntech.cbdmq.pe.servicio.AulaService;
import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import static epntech.cbdmq.pe.constante.MensajesConst.*;

@Service
public class AulaServiceImpl implements AulaService{

	@Autowired
	private AulaRepository repo;
	
	@Override
	public Aula save(Aula obj) throws DataException{
		// TODO Auto-generated method stub
		if(obj.getNombre().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<Aula> objGuardado = repo.findByNombre(obj.getNombre());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}

		return repo.save(obj);
	}

	@Override
	public List<Aula> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Aula> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Aula update(Aula objActualizado) throws DataException{
		
		return repo.save(objActualizado);
	}

	@Override
	public void delete(int id) throws DataException {
		Optional<?> objGuardado = repo.findById(id);
		if (objGuardado.isEmpty()) {
			throw new DataException(REGISTRO_NO_EXISTE);
		}
		repo.deleteById(id);
	}

}
