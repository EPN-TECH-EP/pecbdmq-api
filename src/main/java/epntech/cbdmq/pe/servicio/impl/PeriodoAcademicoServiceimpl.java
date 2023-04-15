package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.PeriodoAcademico;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademicoSemestreModulo;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoMSRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.servicio.PeriodoAcademicoService;

@Service
public class PeriodoAcademicoServiceimpl implements PeriodoAcademicoService {

	@Autowired
	private PeriodoAcademicoRepository repo;
	@Autowired
	private PeriodoAcademicoMSRepository repo1;
	
	@Override
	public PeriodoAcademico save(PeriodoAcademico obj) throws DataException {
		if(obj.getDescripcion().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<PeriodoAcademico> objGuardado = repo.findByDescripcion(obj.getDescripcion());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
		Optional<PeriodoAcademico> objGuardado1 = repo.findByFechaInicioAndFechaFin(obj.getFechaInicio(), obj.getFechaFin());
		if (objGuardado1.isPresent()) {
			throw new DataException(FECHAS_YA_EXISTE);
		}
		return repo.save(obj);
	}

	@Override
	public List<PeriodoAcademico> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<PeriodoAcademico> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public PeriodoAcademico update(PeriodoAcademico objActualizado) throws DataException {
		
		return repo.save(objActualizado);
	}

	@Override
	public void deleteById(int id) throws DataException {
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

	@Override
	public List<PeriodoAcademicoSemestreModulo> getAllPeriodoAcademico() {
		// TODO Auto-generated method stub
		return repo1.getPeriodoAcademico();
	}

	@Override
	public String getEstado() {
		// TODO Auto-generated method stub
		return repo.getEstado();
	}

	@Override
	public Integer updateNextState(Integer id, String proceso) {
		// TODO Auto-generated method stub
		return repo.updateNextState(id, proceso);
	}

	

}
