package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.InstructorMateria;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.InstructorMateriaRepository;

import epntech.cbdmq.pe.servicio.InstructorMateriaService;

@Service
public class InstructorMateriaServiceImpl implements InstructorMateriaService{

	
	
	@Autowired
	private InstructorMateriaRepository repo;
	
	
	@Override
	public InstructorMateria save(InstructorMateria obj) throws DataException {
		// TODO Auto-generated method stub
		return repo.save(obj);
	}

	@Override
	public List<InstructorMateria> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<InstructorMateria> getById(Integer codigo) {
		// TODO Auto-generated method stub
		return repo.findById(codigo);
	}

	@Override
	public InstructorMateria update(InstructorMateria objActualizado) throws DataException {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(Integer codigo) {
		repo.deleteById(codigo);
		
	}
}
