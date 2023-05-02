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

import epntech.cbdmq.pe.dominio.admin.TipoNota;
import epntech.cbdmq.pe.dominio.admin.TipoSancion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.TipoSancionRepository;
import epntech.cbdmq.pe.servicio.TipoSancionService;

/**
 * @author EPN TECH
 * @version $Revision: $
 */
@Service
public class TipoSancionServiceImpl implements TipoSancionService {
    @Autowired
    TipoSancionRepository repo;

    /**
     * {@inheritDoc}
     */
    @Override
    public TipoSancion save(TipoSancion obj) throws DataException {
    	if(obj.getSancion().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<TipoSancion> objGuardado = repo.findBySancionIgnoreCase(obj.getSancion());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
        return repo.save(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TipoSancion> getAll() {
        // TODO Auto-generated method stub
        return repo.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<TipoSancion> getById(Integer codigo) {
        // TODO Auto-generated method stub
        return repo.findById(codigo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TipoSancion update(TipoSancion objActualizado) throws DataException {
    	Optional<TipoSancion> objGuardado = repo.findBySancionIgnoreCase(objActualizado.getSancion());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
        return repo.save(objActualizado);
    }

    /**
     * {@inheritDoc}
     */
    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer codigo) {
        // TODO Auto-generated method stub
        repo.deleteById(codigo);
    }
}
