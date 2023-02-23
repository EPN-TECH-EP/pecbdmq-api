package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.admin.Convocatoria;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.ConvocatoriaRepository;
import epntech.cbdmq.pe.servicio.ConvocatoriaService;

@Service
public class ConvocatoriaServicieImpl implements ConvocatoriaService{

	@Autowired
	private ConvocatoriaRepository repo;
	
	@Override
	public Convocatoria saveData(Convocatoria obj) throws DataException {
		if(obj.getNombre().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<?> objGuardado = repo.findByNombre(obj.getNombre());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
		return repo.save(obj);
	}

	@Override
	public List<Convocatoria> getAllData() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Convocatoria> getByIdData(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Convocatoria updateData(Convocatoria objActualizado) {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void deleteData(int id) throws DataException {
		Optional<?> objGuardado = repo.findById(id);
		if (objGuardado.isEmpty()) {
			throw new DataException(REGISTRO_NO_EXISTE);
		}
		repo.deleteById(id);
	}

}
