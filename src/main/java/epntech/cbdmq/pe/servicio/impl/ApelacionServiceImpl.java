package epntech.cbdmq.pe.servicio.impl;


import static epntech.cbdmq.pe.constante.MensajesConst.APELACION_NO_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.APELACION_CURSO;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Apelacion;

import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.ApelacionRepository;

import epntech.cbdmq.pe.servicio.admin.ApelacionService;

@Service
public class ApelacionServiceImpl implements ApelacionService{
	
	@Autowired
    ApelacionRepository repo;
	
	
	
	@Override
	public Apelacion save(Apelacion obj) throws DataException {
		// TODO Auto-generated method stub
		if(obj.getAprobacion().trim().isEmpty())
			throw new DataException(APELACION_NO_EXISTE);
		Optional<Apelacion> objGuardado = repo.findByaprobacion(obj.getAprobacion());
		if (objGuardado.isPresent()) {
			throw new DataException(APELACION_CURSO);
		}
		
        return repo.save(obj);
        
	}

	@Override
	public List<Apelacion> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Apelacion> getById(Integer codigo) {
		// TODO Auto-generated method stub
		return repo.findById(codigo);
	}

	@Override
	public Apelacion update(Apelacion objActualizado) {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(Integer codigo) {
		// TODO Auto-generated method stub
		
	}

}
