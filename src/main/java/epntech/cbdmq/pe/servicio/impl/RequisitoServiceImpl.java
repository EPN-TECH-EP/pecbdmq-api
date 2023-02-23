package epntech.cbdmq.pe.servicio.impl;


import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.admin.Requisito;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.RequisitoRepository;
import epntech.cbdmq.pe.servicio.RequisitoService;

@Service
public class RequisitoServiceImpl implements RequisitoService {

	@Autowired
	private RequisitoRepository repo;
	
	@Override
	public Requisito save(Requisito obj) throws DataException {
		if(obj.getNombre().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<Requisito> objGuardado = repo.findByNombre(obj.getNombre());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
		return repo.save(obj);
	}

	@Override
	public List<Requisito> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Requisito> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Requisito update(Requisito objActualizado) {
		// TODO Auto-generated method stub
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
