package epntech.cbdmq.pe.servicio.impl;


import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_RELACIONADOS;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import epntech.cbdmq.pe.dominio.admin.CatalogoPreguntas;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.CatalogoPreguntasRepository;
import epntech.cbdmq.pe.servicio.CatalogoPreguntasService;

@Service
public class CatalogoPreguntaServiceImpl implements CatalogoPreguntasService{

	@Autowired
	CatalogoPreguntasRepository repo;
	
	
	@Override
	public CatalogoPreguntas save(CatalogoPreguntas obj) throws DataException {
		// TODO Auto-generated method stub
		if (obj.getPregunta().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<CatalogoPreguntas> objGuardado = repo.findByPreguntaIgnoreCase(obj.getPregunta());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
		obj.setPregunta(obj.getPregunta().toUpperCase());
		return repo.save(obj);
	}

	@Override
	public List<CatalogoPreguntas> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<CatalogoPreguntas> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public CatalogoPreguntas update(CatalogoPreguntas objActualizado) throws DataException {
		if(objActualizado.getPregunta() !=null) {
			Optional<CatalogoPreguntas> objGuardado = repo.findByPreguntaIgnoreCase(objActualizado.getPregunta());
			if (objGuardado.isPresent()&& !objGuardado.get().getCodCatalogoPregunta().equals(objActualizado.getCodCatalogoPregunta())) {
				throw new DataException(REGISTRO_YA_EXISTE);
			}
		}
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
