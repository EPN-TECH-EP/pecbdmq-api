package epntech.cbdmq.pe.servicio.admin;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import epntech.cbdmq.pe.dominio.admin.Menu;
import epntech.cbdmq.pe.dominio.admin.MenuPermisos;
import epntech.cbdmq.pe.repositorio.admin.MenuRepository;

@Service
@Transactional
public class MenuService {
	
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private MenuRepository menuRepository;
    
    @Autowired
    public MenuService(MenuRepository menuRepository) {
    	this.menuRepository = menuRepository;
    }
	
    
    public List<MenuPermisos> findMenuByIdUsuario(@Param("idUsuario") String idUsuario){
    	
    	return this.menuRepository.findMenuByIdUsuario(idUsuario);
    	
    }
    
}
