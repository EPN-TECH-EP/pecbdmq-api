package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import epntech.cbdmq.pe.dominio.admin.MenuRol;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.MenuRolRepository;
import epntech.cbdmq.pe.servicio.MenuRolService;

@Service
@Transactional
public class MenuRolServiceImpl implements MenuRolService{
	
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	private MenuRolRepository menuRolRepository;
	
	@Autowired
    public MenuRolServiceImpl(MenuRolRepository menuRolRepository) {
    	this.menuRolRepository = menuRolRepository;
    }
	
	public MenuRol save(MenuRol menuRol) {
		return this.menuRolRepository.save(menuRol);			
	}
	
	public void delete(MenuRol menuRol) {
		Optional<MenuRol> mr = this.menuRolRepository.findById(menuRol.getMenuRolId());
		if (mr.isPresent()) {
			this.menuRolRepository.delete(mr.get());
		}
	}

	@Override
	public List<MenuRol> getAll() {
		return this.menuRolRepository.findAll();
	}

	@Override
	public MenuRol update(MenuRol objActualizado) throws DataException {
		return this.save(objActualizado);
	}

}
