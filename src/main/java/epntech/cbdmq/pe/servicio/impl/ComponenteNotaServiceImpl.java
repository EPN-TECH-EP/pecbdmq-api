/*
 * Copyright 2008-2023. Todos los derechos reservados.
 */
package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.servicio.PeriodoAcademicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.admin.ComponenteNota;
import epntech.cbdmq.pe.dominio.util.ComponenteTipo;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.ComponenteTipoRepository;
import epntech.cbdmq.pe.repositorio.admin.ComponenteNotaRepository;
import epntech.cbdmq.pe.servicio.ComponenteNotaService;


@Service
public class ComponenteNotaServiceImpl implements ComponenteNotaService {
    @Autowired
    ComponenteNotaRepository repo;

    @Autowired
	PeriodoAcademicoService periodoAcademicoService;

    @Override
    public ComponenteNota save(ComponenteNota obj) throws DataException {
		obj.setCodPeriodoAcademico(periodoAcademicoService.getPAActivo());
		obj.setEstado("ACTIVO");
    	if(obj.getNombre().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<ComponenteNota> objGuardado = repo.findByNombreIgnoreCaseAndCodPeriodoAcademico(obj.getNombre(), periodoAcademicoService.getPAActivo());
		if (objGuardado.isPresent()) {

			// valida si existe eliminado
			ComponenteNota stp = objGuardado.get();
			if (stp.getEstado().compareToIgnoreCase(EstadosConst.ELIMINADO) == 0) {
				stp.setEstado(EstadosConst.ACTIVO);
				return repo.save(stp);
			} else {
			throw new DataException(REGISTRO_YA_EXISTE);
			}

		}

		obj.setNombre(obj.getNombre().toUpperCase());
		return repo.save(obj);
    }

    @Override
    public List<ComponenteNota> getAll() {
        return repo.findAll();
    }


    @Override
    public Optional<ComponenteNota> getById(int id) {
        return repo.findById(id);
    }


    @Override
    public ComponenteNota update(ComponenteNota objActualizado) throws DataException {
    	if(objActualizado.getNombre() !=null) {
    		Optional<ComponenteNota> objGuardado = repo.findByNombreIgnoreCaseAndCodPeriodoAcademico(objActualizado.getNombre(), periodoAcademicoService.getPAActivo());
    		if (objGuardado.isPresent()&& !objGuardado.get().getCodComponenteNota().equals(objActualizado.getCodComponenteNota())) {
    			throw new DataException(REGISTRO_YA_EXISTE);
    		}
    	}
    	objActualizado.setNombre(objActualizado.getNombre().toUpperCase());
    		return repo.save(objActualizado);
    	}


    @Override
    public void delete(int id) {
        repo.deleteById(id);
    }


}
