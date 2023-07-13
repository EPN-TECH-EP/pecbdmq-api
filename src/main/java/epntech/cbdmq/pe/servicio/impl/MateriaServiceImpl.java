package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_RELACIONADOS;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import epntech.cbdmq.pe.servicio.PeriodoAcademicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Materia;
import epntech.cbdmq.pe.dominio.admin.MateriaPeriodo;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademico;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.MateriaPeriodoRepository;
import epntech.cbdmq.pe.repositorio.admin.MateriaRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.servicio.MateriaService;

@Service
public class MateriaServiceImpl implements MateriaService {

	@Autowired
	private MateriaRepository repo;

	@Autowired
	private MateriaPeriodoRepository repo2;


	@Autowired
	private PeriodoAcademicoService periodoAcSvc;

	@Override
	public Materia save(Materia obj) throws DataException {	
		
		if(obj.getNombre().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		
		// verifica si ya existe una materia con ese nombre
		try {
			Optional<Materia> objGuardado = repo.findByNombreIgnoreCase(obj.getNombre());
			if (objGuardado.isPresent()) {
				throw new DataException(REGISTRO_YA_EXISTE);
			}
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
		obj.setNombre(obj.getNombre().toUpperCase());
		
		Materia materia= repo.save(obj);//Guarada el objeto en la materia		
		/*PeriodoAcademico peracademico = new PeriodoAcademico();//Traemos la clase PerAcademico
		peracademico =repo3.getPeriodoAcademicoActivo();//Obtenermos el periodoAcademico Activo
		MateriaPeriodo matperiodo= new MateriaPeriodo();//Traemos la clase Materia Periodo
		matperiodo.setCodPeriodoAcademico(peracademico.getCodigo());//Obtenemos los valores y returnamos valores en la tabla
		matperiodo.setCodMateria(materia.getCodMateria());
		repo2.save(matperiodo);*/
		
		return materia;
	}

	@Override
	public List<Materia> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Materia> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Materia update(Materia objActualizado) throws DataException {
		Optional<Materia> objGuardado = repo.findByNombreIgnoreCase(objActualizado.getNombre());
		
		/*if (objGuardado.isPresent() && !objGuardado.get().getCodMateria().equals(objActualizado.getCodMateria())) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}*/
			return save(objActualizado);
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

	@Override
	public List<Materia> getAllByInstructorPA(Integer codInstructor, String nombreTipoInstructor, Integer periodoAcademico) {
		return repo.getAllByInstructorPA(codInstructor, nombreTipoInstructor, periodoAcademico);
	}

	@Override
	public List<Materia> getAllByCoordinadorPA(Integer codInstructor) {
		return this.getAllByInstructorPA(codInstructor,"COORDINADOR",periodoAcSvc.getPAActivo());
	}


}
