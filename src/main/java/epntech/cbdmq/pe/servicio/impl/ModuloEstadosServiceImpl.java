package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.ModuloEstados;
import epntech.cbdmq.pe.dominio.admin.ModuloEstadosData;
import epntech.cbdmq.pe.repositorio.admin.ModuloEstadosDataRepository;
import epntech.cbdmq.pe.repositorio.admin.ModuloEstadosRepository;
import epntech.cbdmq.pe.servicio.ModuloEstadosService;

@Service
public class ModuloEstadosServiceImpl implements ModuloEstadosService {

	@Autowired
	private ModuloEstadosRepository repo;
	
	@Autowired
	private ModuloEstadosDataRepository repo1;
	
	@Override
	public ModuloEstados save(ModuloEstados obj) {
		// TODO Auto-generated method stub
		return repo.save(obj);
	}

	@Override
	public List<ModuloEstados> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<ModuloEstados> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public ModuloEstados update(ModuloEstados objActualizado) {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}

	@Override
	public List<ModuloEstados> saveAll(List<ModuloEstados> obj) {
		// TODO Auto-generated method stub
		return repo.saveAll(obj);
	}
	
	public List<ModuloEstados> getByModuloEstado(int modulo, int catalogo) {
		// TODO Auto-generated method stub
		return repo.findByModuloAndEstadoCatalogo(modulo, catalogo);
	} 
	
	public List<ModuloEstadosData> getByModulo(int modulo) {
		// TODO Auto-generated method stub
		return repo1.getByModulo(modulo);
	}

	@Override
	public List<ModuloEstadosData> getAllData() {
		// TODO Auto-generated method stub
		return repo1.getAllData();
	} 

}
