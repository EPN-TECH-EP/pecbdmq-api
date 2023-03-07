package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Sanciones;

import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.SancionesRepository;
import epntech.cbdmq.pe.servicio.SancionesService;

@Service
public class SancionesServiceImpl implements SancionesService {

	@Autowired
	SancionesRepository repo;
	
	@Override
	public Sanciones save(Sanciones obj) throws DataException {
		if(obj.getOficialsemana().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<Sanciones> objGuardado = repo.findByoficialsemana(obj.getOficialsemana());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
        return repo.save(obj);
	}

	@Override
	public List<Sanciones> getAll() {
		// TODO Auto-generated method stub
		 return repo.findAll();
	}

	@Override
	public Optional<Sanciones> getById(Integer codigo) {
		// TODO Auto-generated method stub
		return repo.findById(codigo);
	}

	@Override
	public Sanciones update(Sanciones objActualizado) {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(Integer codigo) {
		// TODO Auto-generated method stub
		repo.deleteById(codigo);
	}

}
