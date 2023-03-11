package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_RELACIONADOS;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Notas;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.NotaRepository;

import epntech.cbdmq.pe.servicio.NotaService;

@Service
public class NotasServiceImpl implements NotaService {

	@Autowired
	private NotaRepository repo;
	
	
	@Override
	public Notas save(Notas obj) throws DataException {
		if(obj.getUsuariocreanota().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<?> objGuardado = repo.findByusuariocreanota(obj.getUsuariocreanota());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
		return repo.save(obj);
	}

	@Override
	public List<Notas> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Notas> getById(Integer codigo) {
		// TODO Auto-generated method stub
		return repo.findById(codigo);
	}

	@Override
	public Notas update(Notas objActualizado) throws DataException {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(Integer codigo) throws DataException {
		Optional<?> objGuardado = repo.findById(codigo);
		if (objGuardado.isEmpty()) {
			throw new DataException(REGISTRO_NO_EXISTE);
		}
		try {
			repo.deleteById(codigo);
		} catch (Exception e) {
			if (e.getMessage().contains("constraint")) {
				throw new DataException(DATOS_RELACIONADOS);
			}
		
	}
	}	 

}
