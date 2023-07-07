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
import epntech.cbdmq.pe.dominio.admin.TipoNota;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.TipoNotaRepository;
import epntech.cbdmq.pe.servicio.TipoNotaService;

/**
 * @author EPN TECH
 * @version $Revision: $
 */
@Service
public class TipoNotaServiceImpl implements TipoNotaService {
    @Autowired
    TipoNotaRepository repo;

    /**
     * {@inheritDoc}
     */
    @Override
    public TipoNota save(TipoNota obj) throws DataException {
    	if(obj.getNota().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<TipoNota> objGuardado = repo.findByNotaIgnoreCase(obj.getNota());
		if (objGuardado.isPresent()) {

			// valida si existe eliminado
			TipoNota stp = objGuardado.get();
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
    public List<TipoNota> getAll() {
        // TODO Auto-generated method stub
        return repo.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<TipoNota> getById(Integer codigo) {
        // TODO Auto-generated method stub
        return repo.findById(codigo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TipoNota update(TipoNota objActualizado) throws DataException {
    	Optional<TipoNota> objGuardado = repo.findByNotaIgnoreCase(objActualizado.getNota());
    	if (objGuardado.isPresent()&& !objGuardado.get().getCodTipoNota().equals(objActualizado.getCodTipoNota())) {
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
