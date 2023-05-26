package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Materia;
import epntech.cbdmq.pe.dominio.admin.MateriaPeriodo;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademico;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.MateriaPeriodoRepository;
import epntech.cbdmq.pe.repositorio.admin.MateriaRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.servicio.MateriaPeriodoService;
@Service
public class MateriaPeriodoServiceImpl implements MateriaPeriodoService{

	@Autowired
	private MateriaPeriodoRepository repo;
	@Autowired
	MateriaRepository repo2;


	@Autowired
	private PeriodoAcademicoRepository repo3;
	
	@Override
	public MateriaPeriodo save(MateriaPeriodo obj) throws DataException {
		return repo.save(obj);
		
	}

	@Override
	public List<MateriaPeriodo> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<MateriaPeriodo> getById(Integer codigo) {
		// TODO Auto-generated method stub
		return repo.findById(codigo);
	}

	@Override
	public MateriaPeriodo update(MateriaPeriodo objActualizado) throws DataException {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(Integer codigo) {
		// TODO Auto-generated method stub
		repo.deleteById(codigo);
	}
	}
	
	


