package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public Estados update(Estados objActualizado) {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}

}
