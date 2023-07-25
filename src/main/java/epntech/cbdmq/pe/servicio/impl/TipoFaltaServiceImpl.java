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
import epntech.cbdmq.pe.dominio.admin.TipoFalta;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.TipoFaltaRepository;
import epntech.cbdmq.pe.servicio.TipoFaltaService;

/**
 * @author EPN TECH
 * @version $Revision: $
 */
@Service
public class TipoFaltaServiceImpl implements TipoFaltaService {
    @Autowired
    TipoFaltaRepository repo;

    /**
     * {@inheritDoc}
     */
    @Override
    public TipoFalta save(TipoFalta obj) throws DataException {
    	if(obj.getNombreFalta().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<TipoFalta> objGuardado = repo.findByNombreFaltaIgnoreCase(obj.getNombreFalta());
		if (objGuardado.isPresent()) {

			// valida si existe eliminado
			TipoFalta stp = objGuardado.get();
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
    public List<TipoFalta> getAll() {
        // TODO Auto-generated method stub
        return repo.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<TipoFalta> getById(Integer codigo) {
        // TODO Auto-generated method stub
        return repo.findById(codigo);
    }

    /**
     * {@inheritDoc}
     */

    public TipoFalta update(TipoFalta objActualizado) throws DataException {
    	Optional<TipoFalta> objGuardado = repo.findByNombreFaltaIgnoreCase(objActualizado.getNombreFalta());
    	if (objGuardado.isPresent()&& !objGuardado.get().getCodTipoFalta().equals(objActualizado.getCodTipoFalta())) {
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
