/*
 * Copyright 2008-2023. Todos los derechos reservados.
 */
package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.TipoPrueba;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.TipoPruebaRepository;
import epntech.cbdmq.pe.servicio.TipoPruebaService;

/**
 * @author EPN TECH
 * @version $Revision: $
 */
@Service
public class TipoPruebaServiceImpl implements TipoPruebaService {
    @Autowired
    TipoPruebaRepository repo;

    /**
     * {@inheritDoc}
     */
    @Override
    public TipoPrueba save(TipoPrueba obj) throws DataException {
        // TODO Auto-generated method stub
        return repo.save(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TipoPrueba> getAll() {
        // TODO Auto-generated method stub
        return repo.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<TipoPrueba> getById(Integer codigo) {
        // TODO Auto-generated method stub
        return repo.findById(codigo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TipoPrueba update(TipoPrueba objActualizado) {
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
