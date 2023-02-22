/*
 * Copyright 2008-2023. Todos los derechos reservados.
 */
package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.TipoBaja;
import epntech.cbdmq.pe.repositorio.admin.TipoBajaRepository;
import epntech.cbdmq.pe.servicio.TipoBajaService;

/**
 * @author EPN TECH
 * @version $Revision: $
 */
@Service
public class TipoBajaServiceImpl implements TipoBajaService {
    @Autowired
    TipoBajaRepository repo;

    /**
     * {@inheritDoc}
     */
    @Override
    public TipoBaja save(TipoBaja obj) {
        // TODO Auto-generated method stub
        return repo.save(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TipoBaja> getAll() {
        // TODO Auto-generated method stub
        return repo.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<TipoBaja> getById(Integer codigo) {
        // TODO Auto-generated method stub
        return repo.findById(codigo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TipoBaja update(TipoBaja objActualizado) {
        // TODO Auto-generated method stub
        return repo.save(objActualizado);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer codigo) {
        // TODO Auto-generated method stub
        repo.deleteById(codigo);
    }
    /**
     * {@inheritDoc}
     */
}
