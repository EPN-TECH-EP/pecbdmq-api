/*
 * Copyright 2008-2023. Todos los derechos reservados.
 */
package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.ComponenteNota;
import epntech.cbdmq.pe.dominio.util.ComponenteTipo;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.ComponenteTipoRepository;
import epntech.cbdmq.pe.repositorio.admin.ComponenteNotaRepository;
import epntech.cbdmq.pe.servicio.ComponenteNotaService;

/**
 * @author EPN TECH
 * @version $Revision: $
 */
@Service
public class ComponenteNotaServiceImpl implements ComponenteNotaService {
    @Autowired
    ComponenteNotaRepository repo;

    @Autowired
    ComponenteTipoRepository componentetiporepository;
    /**
     * {@inheritDoc}
     */
    @Override
    public ComponenteNota save(ComponenteNota obj) throws DataException {
    	// TODO Auto-generated method stub
    	
    	if(obj.getNombre().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<ComponenteNota> objGuardado = repo.findByNombreIgnoreCase(obj.getNombre());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}

		obj.setNombre(obj.getNombre().toUpperCase());
		return repo.save(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ComponenteNota> getAll() {
        // TODO Auto-generated method stub
        return repo.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ComponenteNota> getById(int id) {
        // TODO Auto-generated method stub
        return repo.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ComponenteNota update(ComponenteNota objActualizado) throws DataException {
    	if(objActualizado.getNombre() !=null) {
    		Optional<ComponenteNota> objGuardado = repo.findByNombreIgnoreCase(objActualizado.getNombre());
    		if (objGuardado.isPresent()&& !objGuardado.get().getCodComponenteNota().equals(objActualizado.getCodComponenteNota())) {
    			throw new DataException(REGISTRO_YA_EXISTE);
    		}
    	}
    	objActualizado.setNombre(objActualizado.getNombre().toUpperCase());
    		return repo.save(objActualizado);
    	}

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(int id) {
        repo.deleteById(id);
    }

	@Override
	public List<ComponenteTipo> getComponenteTipo() {
		return componentetiporepository.getComponenteTipo();
	}
}
