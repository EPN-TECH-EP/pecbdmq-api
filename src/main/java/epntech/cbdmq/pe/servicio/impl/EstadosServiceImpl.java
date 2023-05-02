

package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.admin.Estados;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.EstadosRepository;
import epntech.cbdmq.pe.servicio.EstadosService;

@Service
public class EstadosServiceImpl implements EstadosService {

	@Autowired
	private EstadosRepository repo;
	
	@Override
	public Estados save(Estados obj) throws DataException {
		if (obj.getNombre() == null) 
			throw new DataException(REGISTRO_VACIO);
		if(obj.getNombre().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		if(obj.getNombre().trim().isBlank())
			throw new DataException(REGISTRO_VACIO);
		Optional<Estados> objGuardado = repo.findByNombreIgnoreCase(obj.getNombre());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}

		obj.setNombre(obj.getNombre().toUpperCase());	
		return repo.save(obj);
	}

	@Override
	public List<Estados> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Estados> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Estados update(Estados objActualizado) throws DataException {
		Optional<Estados> objGuardado = repo.findByNombreIgnoreCase(objActualizado.getNombre());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}

		objActualizado.setNombre(objActualizado.getNombre().toUpperCase());
		return repo.save(objActualizado);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}

}

