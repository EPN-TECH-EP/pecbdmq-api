package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.CatalogoCurso;
import epntech.cbdmq.pe.dominio.fichaPersonal.especializacion.EspecializacionInstructor;
import epntech.cbdmq.pe.dominio.fichaPersonal.formacion.FormacionInstructor;
import epntech.cbdmq.pe.dominio.fichaPersonal.profesionalizacion.ProfesionalizacionInstructor;
import epntech.cbdmq.pe.dominio.util.*;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.fichaPersonal.Instructor;

import epntech.cbdmq.pe.dominio.admin.PeriodoAcademico;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.InstructorPeriodoRepository;

import epntech.cbdmq.pe.dominio.admin.InstructorMateria;
import epntech.cbdmq.pe.repositorio.admin.InstructorDatosRepository;
import epntech.cbdmq.pe.repositorio.admin.InstructorMateriaRepository;
import epntech.cbdmq.pe.repositorio.fichaPersonal.InstructorRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.servicio.InstructorService;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

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
	@Autowired
	private InstructorDatosRepository instructorDatosRepository;
	
	@Override
	public Instructor save(Instructor obj) throws DataException {
		Instructor instructor = new Instructor();
		
		try {
			instructor = repo.save(obj);
		/*PeriodoAcademico peracademico = new PeriodoAcademico();
			peracademico = repo3.getPeriodoAcademicoActivo();
			InstructorPeriodo insperiodo = new InstructorPeriodo();
		insperiodo.setCodInstructor(instructor.getCodInstructor());
		insperiodo.setCodPeriodoAcademico(peracademico.getCodigo());
				repo2.save(insperiodo);*/
		
		} catch (Exception e) {
			//System.out.println("e.getMessage(): " + e.getMessage());
			if (e.getMessage().contains("constraint")) {
				throw new DataException(REGISTRO_YA_EXISTE);
			}
		}

		return instructor;
	}

	@Override
	public List<InstructorDatos> getAll() {
		return instructorDatosRepository.getAllInstructorDatos();
	}

	@Override
	public List<Instructor> getAllInstructor() {
		return repo.findAll();
	}

	@Override
	public Optional<Instructor> getById(Integer codigo) {
		return repo.findById(codigo);
	}

	@Override
	public Instructor update(InstructorDatos objActualizado) {
		Instructor instructor = repo.findById(objActualizado.getCodInstructor().intValue())
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

		instructor.setCodTipoProcedencia(objActualizado.getCodTipoProcedencia());
		instructor.setCodEstacion(objActualizado.getCodEstacion());
		instructor.setCodUnidadGestion(objActualizado.getCodUnidadGestion());
		instructor.setCodTipoContrato(objActualizado.getCodTipoContrato());

		return repo.save(instructor);
	}

	@Override
	public void delete(Integer codigo) {
		Instructor instructor = repo.findById(codigo)
				.orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));
		repo.delete(instructor);
	}

	@Override
	public void saveAllMaterias(List<InstructorMateria> obj) {
		instructorMateriaRepository.saveAll(obj);
	}
	@Override
	public Instructor getInstructorByUser(String coduser) {
		return repo.getInstructorByUsuario(coduser);
	}



}
