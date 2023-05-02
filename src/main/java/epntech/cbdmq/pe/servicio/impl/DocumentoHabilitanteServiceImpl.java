package epntech.cbdmq.pe.servicio.impl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.DocumentoHabilitante;
import epntech.cbdmq.pe.repositorio.admin.DocumentoHabilitanteRepository;
import epntech.cbdmq.pe.servicio.DocumentoHabilitanteService;
import static epntech.cbdmq.pe.constante.MensajesConst.*;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

@Service
public class DocumentoHabilitanteServiceImpl implements DocumentoHabilitanteService {

	@Autowired
	private DocumentoHabilitanteRepository repo;

	@Override
	public DocumentoHabilitante save(DocumentoHabilitante obj) throws DataException {
		if(obj.getNombre().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<DocumentoHabilitante> objGuardado = repo.findByNombre(obj.getNombre());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
		obj.setNombre(obj.getNombre().toUpperCase());
		return repo.save(obj);
	}

	@Override
	public List<DocumentoHabilitante> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<DocumentoHabilitante> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public DocumentoHabilitante update(DocumentoHabilitante objActualizado) throws DataException {
		
		return repo.save(objActualizado);
	}

	@Override
	public void delete(int id) throws DataException {
		Optional<?> objGuardado = repo.findById(id);
		if (objGuardado.isEmpty()) {
			throw new DataException(REGISTRO_NO_EXISTE);
		}
		try {
			repo.deleteById(id);
		} catch (Exception e) {
			if (e.getMessage().contains("constraint")) {
				throw new DataException(DATOS_RELACIONADOS);
			}
		}
	}
	
	
}
