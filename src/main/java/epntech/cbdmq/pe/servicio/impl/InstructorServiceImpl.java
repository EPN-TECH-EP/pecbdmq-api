package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.EspCurso;
import epntech.cbdmq.pe.dominio.admin.Instructor;

import epntech.cbdmq.pe.dominio.admin.PeriodoAcademico;
import epntech.cbdmq.pe.dominio.util.InstructorPeriodo;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.InstructorPeriodoRepository;

import epntech.cbdmq.pe.dominio.admin.InstructorMateria;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.InstructorMateriaRepository;
import epntech.cbdmq.pe.repositorio.admin.InstructorRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.servicio.InstructorService;

@Service
public class InstructorServiceImpl implements InstructorService {
	
	@Autowired
	private InstructorRepository repo;
	@Autowired
	private InstructorMateriaRepository instructorMateriaRepository; 

	@Autowired
	private InstructorPeriodoRepository repo2;
	
	@Autowired
	private PeriodoAcademicoRepository repo3;
	
	@Override
	public Instructor save(Instructor obj) throws DataException {
		Instructor instructor = repo.save(obj);
		PeriodoAcademico peracademico = new PeriodoAcademico();
		peracademico =repo3.getPeriodoAcademicoActivo();
		InstructorPeriodo insperiodo= new InstructorPeriodo(); 
		insperiodo.setCod_instructor(instructor.getCod_instructor());
		insperiodo.setCod_periodo_academico(peracademico.getCodigo());
				repo2.save(insperiodo);
		
		return instructor;
	}

	@Override
	public List<Instructor> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Instructor> getById(Integer codigo) {
		// TODO Auto-generated method stub
		return repo.findById(codigo);
	}

	@Override
	public Instructor update(Instructor objActualizado) {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(Integer codigo) {
		// TODO Auto-generated method stub
		repo.deleteById(codigo);
	}

	@Override
	public void saveAllMaterias(List<InstructorMateria> obj) {
		// TODO Auto-generated method stub
		instructorMateriaRepository.saveAll(obj);
	}

}
