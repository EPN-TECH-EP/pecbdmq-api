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

import epntech.cbdmq.pe.dominio.admin.TipoFecha;
import epntech.cbdmq.pe.dominio.admin.TipoNota;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.TipoFechaRepository;
import epntech.cbdmq.pe.servicio.TipoFechaService;

/**
 * @author EPN TECH
 * @version $Revision: $
 */
@Service
public class TipoFechaServiceImpl implements TipoFechaService {
    @Autowired
    TipoFechaRepository repo;

    /**
     * {@inheritDoc}
     */
    @Override
    public TipoFecha save(TipoFecha obj) throws DataException {
    	if(obj.getFecha().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<TipoFecha> objGuardado = repo.findByFecha(obj.getFecha());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
        return repo.save(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TipoFecha> getAll() {
        // TODO Auto-generated method stub
        return repo.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<TipoFecha> getById(String fecha) {
        // TODO Auto-generated method stub
        return repo.findById(fecha);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TipoFecha update(TipoFecha objActualizado) {
        return repo.save(objActualizado);
    }

    /**
     * {@inheritDoc}
     */
    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(String fecha) {
        // TODO Auto-generated method stub
        repo.deleteById(fecha);
    }
}