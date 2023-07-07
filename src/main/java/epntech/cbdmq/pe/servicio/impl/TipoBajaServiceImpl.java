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

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.admin.SubTipoPrueba;
import epntech.cbdmq.pe.dominio.admin.TipoBaja;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.TipoBajaRepository;
import epntech.cbdmq.pe.servicio.TipoBajaService;

/**
 * @author EPN TECH
 * @version $Revision: $
 */
@Service
public class TipoBajaServiceImpl implements TipoBajaService {
    @Autowired
    private TipoBajaRepository repo;

    /**
     * {@inheritDoc}
     * @throws DataException 
     */
    @Override
    public TipoBaja save(TipoBaja obj) throws DataException {
    	if(obj.getBaja().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<TipoBaja> objGuardado = repo.findByBajaIgnoreCase(obj.getBaja());
		if (objGuardado.isPresent()) {

			// valida si existe eliminado
			TipoBaja stp = objGuardado.get();
			if (stp.getEstado().compareToIgnoreCase(EstadosConst.ELIMINADO) == 0) {
				stp.setEstado(EstadosConst.ACTIVO);
				return repo.save(stp);
			} else {
			throw new DataException(REGISTRO_YA_EXISTE);
			}

		}
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
    public TipoBaja update(TipoBaja objActualizado) throws DataException  {
    	Optional<TipoBaja> objGuardado = repo.findByBajaIgnoreCase(objActualizado.getBaja());
    	if (objGuardado.isPresent()&& !objGuardado.get().getCodTipoBaja().equals(objActualizado.getCodTipoBaja())) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
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
