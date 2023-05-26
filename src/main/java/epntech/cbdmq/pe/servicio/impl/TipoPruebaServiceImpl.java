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

   
    @Override
    public TipoPrueba save(TipoPrueba obj) throws DataException {
    	if(obj.getTipoPrueba().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<TipoPrueba> objGuardado = repo.findByTipoPruebaIgnoreCase(obj.getTipoPrueba());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
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
    public TipoPrueba update(TipoPrueba objActualizado)  throws DataException{
    	Optional<TipoPrueba> objGuardado = repo.findByTipoPruebaIgnoreCase(objActualizado.getTipoPrueba());
    	if (objGuardado.isPresent()&& !objGuardado.get().getCodTipoPrueba().equals(objActualizado.getCodTipoPrueba())) {
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
