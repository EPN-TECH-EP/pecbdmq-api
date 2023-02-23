/*
 * Copyright 2008-2023. Todos los derechos reservados.
 */
package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.ComponenteNota;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public ComponenteNota save(ComponenteNota obj) throws DataException {
        // TODO Auto-generated method stub
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
    public ComponenteNota update(ComponenteNota objActualizado) {
        return repo.save(objActualizado);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(int id) {
        repo.deleteById(id);
    }
}
