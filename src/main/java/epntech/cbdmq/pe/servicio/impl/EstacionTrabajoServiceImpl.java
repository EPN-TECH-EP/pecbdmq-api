package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.admin.EstacionTrabajo;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.EstacionTrabajoRepository;
import epntech.cbdmq.pe.servicio.EstacionTrabajoService;

@Service
public class EstacionTrabajoServiceImpl implements EstacionTrabajoService {

	@Autowired
	private EstacionTrabajoRepository repo;
	
	@Override
	public EstacionTrabajo save(EstacionTrabajo obj) throws DataException {
		if (obj.getNombre().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<EstacionTrabajo> objGuardado = repo.findByNombreIgnoreCase(obj.getNombre());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}

		obj.setNombre(obj.getNombre().toUpperCase());
		return repo.save(obj);
	}

	@Override
	public List<EstacionTrabajo> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<EstacionTrabajo> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public EstacionTrabajo update(EstacionTrabajo objActualizado) throws DataException {
		Optional<EstacionTrabajo> objGuardado = repo.findByNombreIgnoreCase(objActualizado.getNombre());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}

		objActualizado.setNombre(objActualizado.getNombre().toUpperCase());
		return repo.save(objActualizado);
	}

	@Override
	public void delete(int id) throws DataException {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}

}
