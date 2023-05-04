package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.admin.Menu;
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
		if (obj.getNombre().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<Rol> objGuardado = rolRepository.findByNombreIgnoreCase(obj.getNombre());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}

		obj.setNombre(obj.getNombre().toUpperCase());
		return rolRepository.save(obj);
	}

	@Override
	public Optional<Rol> getById(Long id) {
		return rolRepository.findById(id);
	}

	@Override
	public Rol update(Rol objActualizado) throws DataException {
		
		if(objActualizado.getNombre() !=null) {
			Optional<Rol> objGuardado = rolRepository.findByNombreIgnoreCase(objActualizado.getNombre());
			if (objGuardado.isPresent()&& !objGuardado.get().getCodRol().equals(objActualizado.getCodRol())) {
				throw new DataException(REGISTRO_YA_EXISTE);
			}
		}
		return this.save(objActualizado);
		}
		


	@Override
	public void delete(Long id) throws DataException {
		this.rolRepository.deleteById(id);
		
	}
	
	

}
