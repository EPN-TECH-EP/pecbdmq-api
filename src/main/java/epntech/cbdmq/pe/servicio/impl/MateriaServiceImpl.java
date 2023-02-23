package epntech.cbdmq.pe.servicio.impl;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.admin.Materia;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.MateriaRepository;
import epntech.cbdmq.pe.servicio.MateriaService;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

@Service
public class MateriaServiceImpl implements MateriaService {

	@Autowired
	private MateriaRepository repo;

	@Override
	public Materia save(Materia obj) throws DataException {
		if(obj.getNombreMateria().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<Materia> objGuardado = repo.findByNombreMateria(obj.getNombreMateria());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
		
		return repo.save(obj);
	}

	@Override
	public List<Materia> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Materia> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Materia update(Materia objActualizado) throws DataException {
		
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
