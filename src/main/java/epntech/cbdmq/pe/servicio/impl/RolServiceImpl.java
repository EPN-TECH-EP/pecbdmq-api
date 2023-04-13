package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import epntech.cbdmq.pe.dominio.admin.Rol;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.RolRepository;
import epntech.cbdmq.pe.servicio.RolService;

@Service
@Transactional
public class RolServiceImpl implements RolService{

	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	private RolRepository rolRepository;

	@Autowired
	public RolServiceImpl(RolRepository rolRepository) {
		this.rolRepository = rolRepository;
	}
	
	public List<Rol> getAll(){
		return this.rolRepository.findAll();
	}

	@Override
	public Rol save(Rol obj) throws DataException {
		return this.rolRepository.save(obj);
	}

	@Override
	public Optional<Rol> getById(Long id) {
		return rolRepository.findById(id);
	}

	@Override
	public Rol update(Rol objActualizado) throws DataException {
		return this.save(objActualizado);
	}

	@Override
	public void delete(Long id) throws DataException {
		this.rolRepository.deleteById(id);
		
	}
	
	

}
