package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		// TODO Auto-generated method stub
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
	public void delete(int id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}
	
	
}
