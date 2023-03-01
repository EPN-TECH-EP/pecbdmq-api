package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_RELACIONADOS;
import static epntech.cbdmq.pe.constante.MensajesConst.FECHAS_YA_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.PeriodoAcademico;
import epntech.cbdmq.pe.dominio.admin.PeriodoEvaluacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.PeriodoEvaluacionRepository;
import epntech.cbdmq.pe.servicio.PeriodoEvaluacionService;

@Service
public class PeriodoEvaluacionServiceImpl implements PeriodoEvaluacionService {

	@Autowired
	PeriodoEvaluacionRepository repo;

	@Override
	public PeriodoEvaluacion save(PeriodoEvaluacion obj) throws DataException {
		if(obj.getDescripcion().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<PeriodoEvaluacion> objGuardado = repo.findByDescripcion(obj.getDescripcion());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
		Optional<PeriodoEvaluacion> objGuardado1 = repo.findByFechaInicioAndFechaFin(obj.getFechaInicio(), obj.getFechaFin());
		if (objGuardado1.isPresent()) {
			throw new DataException(FECHAS_YA_EXISTE);
		}
		return repo.save(obj);
	}

	@Override
	public List<PeriodoEvaluacion> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<PeriodoEvaluacion> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public PeriodoEvaluacion update(PeriodoEvaluacion objActualizado) {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(int id) throws DataException {
		Optional<?> objGuardado = repo.findById(id);
		if (objGuardado.isEmpty()) {
			throw new DataException(REGISTRO_NO_EXISTE);
		}
		try {
			repo.deleteById(id);
		} catch (Exception e) {
			if (e.getMessage().contains("constraint")) {
				throw new DataException(DATOS_RELACIONADOS);
			}
		}
	}
	
	
}
