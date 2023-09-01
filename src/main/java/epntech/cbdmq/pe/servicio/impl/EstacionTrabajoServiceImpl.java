package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.util.EstacionTrabajoDto;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.admin.EstacionTrabajo;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.EstacionTrabajoRepository;
import epntech.cbdmq.pe.servicio.EstacionTrabajoService;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

@Service
public class EstacionTrabajoServiceImpl implements EstacionTrabajoService {

	@Autowired
	private EstacionTrabajoRepository repo;
	
	@Override
	public EstacionTrabajoDto save(EstacionTrabajo obj) {
		if (obj.getNombre().trim().isEmpty())
			throw new BusinessException(REGISTRO_VACIO);
		Optional<EstacionTrabajo> objGuardado = repo.findByNombreIgnoreCase(obj.getNombre());
		if (objGuardado.isPresent()) {
			// valida si existe eliminado
			EstacionTrabajo stp = objGuardado.get();
			if (stp.getEstado().compareToIgnoreCase(EstadosConst.ELIMINADO) == 0) {
				stp.setEstado(EstadosConst.ACTIVO);
				repo.save(stp);
				return getById(stp.getCodigo());
			} else {
				throw new BusinessException(REGISTRO_YA_EXISTE);
			}

		}

		obj.setNombre(obj.getNombre().toUpperCase());
		repo.save(obj);
		return getById(obj.getCodigo());
	}

	@Override
	public List<EstacionTrabajoDto> getAll() {
		return repo.findAllWithProvince();
	}

	@Override
	public EstacionTrabajoDto getById(int id) {
		return repo.findWithProvince(id)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));
	}

	@Override
	public EstacionTrabajoDto update(EstacionTrabajo objActualizado) {
		EstacionTrabajo estacionTrabajo = repo.findById(objActualizado.getCodigo())
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));
		estacionTrabajo.setCanton(objActualizado.getCanton());
		estacionTrabajo.setNombre(objActualizado.getNombre());

		if(estacionTrabajo.getNombre() !=null) {
			Optional<EstacionTrabajo> objGuardado = repo.findByNombreIgnoreCase(estacionTrabajo.getNombre());
			if (objGuardado.isPresent()&& !objGuardado.get().getCodigo().equals(estacionTrabajo.getCodigo())) {
				throw new BusinessException(REGISTRO_YA_EXISTE);
			}
		}
		repo.save(estacionTrabajo);
		return getById(estacionTrabajo.getCodigo());
	}

	@Override
	public void delete(int id) {
		repo.deleteById(id);
	}

}
