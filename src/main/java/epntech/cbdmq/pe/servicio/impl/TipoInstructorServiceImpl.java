package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.TipoInstructor;
import epntech.cbdmq.pe.dominio.admin.TipoNota;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.TipoInstructorRepository;
import epntech.cbdmq.pe.repositorio.admin.TipoNotaRepository;
import epntech.cbdmq.pe.servicio.TipoInstructorService;

@Service
public class TipoInstructorServiceImpl implements TipoInstructorService{

	  @Autowired
	  TipoInstructorRepository repo;
	
	@Override
	public TipoInstructor save(TipoInstructor obj) throws DataException {
		if(obj.getNombretipoinstructor().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<TipoInstructor> objGuardado = repo.findBynombretipoinstructor(obj.getNombretipoinstructor());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}

		return repo.save(obj);
	}

	@Override
	public List<TipoInstructor> getAll() {
		return repo.findAll();
	}

	@Override
	public Optional<TipoInstructor> getById(Integer codigo) {
		// TODO Auto-generated method stub
		return repo.findById(codigo);
	}

	@Override
	public TipoInstructor update(TipoInstructor objActualizado) {
		return repo.save(objActualizado);
	}

	@Override
	public void delete(Integer codigo) {
		// TODO Auto-generated method stub
		 repo.deleteById(codigo);
	}

}
