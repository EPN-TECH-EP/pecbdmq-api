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
import epntech.cbdmq.pe.servicio.MateriaService;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

@Service
public class MateriaServiceImpl implements MateriaService {

	@Autowired
	private MateriaRepository repo;

	@Autowired
	private MateriaPeriodoRepository repo2;


	@Autowired
	private PeriodoAcademicoRepository repo3;

	@Override
	public Materia save(Materia obj) throws DataException {
		Materia materia= repo.save(obj);//Guarada el objeto en la materia		
		PeriodoAcademico peracademico = new PeriodoAcademico();//Traemos la clase PerAcademico
		peracademico =repo3.getPeriodoAcademicoActivo();//Obtenermos el periodoAcademico Activo
		MateriaPeriodo matperiodo= new MateriaPeriodo();//Traemos la clase Materia Periodo
		matperiodo.setCod_periodo_academico(peracademico.getCodigo());//Obtenemos los valores y returnamos valores en la tabla
		matperiodo.setCod_materia(materia.getCodMateria());		
		repo2.save(matperiodo);
		
		if(obj.getNombre().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<Materia> objGuardado = repo.findByNombreIgnoreCase(obj.getNombre());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
		obj.setNombre(obj.getNombre().toUpperCase());
		return repo.save(obj);
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
		if (objGuardado.isPresent()&& !objGuardado.get().getCodMateria().equals(objActualizado.getCodMateria())) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
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
