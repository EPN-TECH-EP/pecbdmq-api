package epntech.cbdmq.pe.servicio.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Estudiante;
import epntech.cbdmq.pe.dominio.admin.EstudianteFor;
import epntech.cbdmq.pe.dominio.util.AspirantesDatos;
import epntech.cbdmq.pe.dominio.util.EstudianteDatos;
import epntech.cbdmq.pe.repositorio.admin.AspirantesRepository;
import epntech.cbdmq.pe.repositorio.admin.EstudianteForRepository;
import epntech.cbdmq.pe.repositorio.admin.EstudianteRepository;
import epntech.cbdmq.pe.servicio.EstudianteService;

@Service
public class EstudianteServiceImpl implements EstudianteService {

	@Autowired
	private EstudianteRepository repo;
	@Autowired
	private EstudianteForRepository estudianteForRepository;
	@Autowired
	private AspirantesRepository aspirantesRepository;
	
	
	@Override
	public Estudiante save(Estudiante obj) {
		// TODO Auto-generated method stub
		return repo.save(obj);
	}

	@Override
	public List<Estudiante> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Estudiante> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Estudiante update(Estudiante objActualizado) {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}

	@Override
	public Optional<Estudiante> getByIdEstudiante(String id) {
		// TODO Auto-generated method stub
		return repo.findByidEstudiante(id);
	}

	/*@Override
	public Page<EstudianteDatos> getAllEstudiante(Pageable pageable) throws Exception {
		// TODO Auto-generated method stub
		return repo.findAllEstudiante(pageable);
	}

	@Override
	public List<EstudianteDatos> findAllEstudiante() {
		// TODO Auto-generated method stub
		return this.repo.findAllEstudiante();
	}*/

	@Override
	public void saveEstudiantes() {
		// TODO Auto-generated method stub
		estudianteForRepository.insertEstudiantes();
	}

	
}
