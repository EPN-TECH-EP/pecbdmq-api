package epntech.cbdmq.pe.servicio.admin;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.admin.Menu;
import epntech.cbdmq.pe.dominio.admin.MenuPermisos;
import epntech.cbdmq.pe.dominio.admin.MenuRol;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
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

	public List<MenuPermisos> findMenuByIdUsuario(@Param("idUsuario") String idUsuario) {
		return this.menuRepository.findMenuByIdUsuario(idUsuario);
	}
	
	public List<Menu> getAll(){
		return this.menuRepository.findAll();
	}
	
	public Optional<Menu> getById(Integer id) {
		// TODO Auto-generated method stub
		return menuRepository.findById(id);
	}
	
public Menu save(Menu menu) throws DataException {
	if (menu.getEtiqueta().trim().isEmpty())
		throw new DataException(REGISTRO_VACIO);
	Menu objGuardado = menuRepository.findMenuByEtiqueta(menu.getEtiqueta());
	if (objGuardado !=null) {
		throw new DataException(REGISTRO_YA_EXISTE);
	}

	menu.setEtiqueta(menu.getEtiqueta().toUpperCase());
	return menuRepository.save(menu);
		
	}
	
	public Menu update(Menu menu) throws DataException {
		if(menu.getEtiqueta() !=null) {
			Menu objGuardado = menuRepository.findMenuByEtiqueta(menu.getEtiqueta());
			if (objGuardado!=null&& !objGuardado.getCodMenu().equals(menu.getCodMenu())) {
				throw new DataException(REGISTRO_YA_EXISTE);
			}
		}
		return this.save(menu);
	}
	
	

}
