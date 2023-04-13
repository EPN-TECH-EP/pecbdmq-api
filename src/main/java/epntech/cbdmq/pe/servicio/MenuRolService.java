package epntech.cbdmq.pe.servicio;

import java.util.List;

import epntech.cbdmq.pe.dominio.admin.MenuRol;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface MenuRolService {

	List<MenuRol> getAll();	
	
	MenuRol save(MenuRol obj) throws DataException;

	MenuRol update(MenuRol objActualizado) throws DataException;

	void delete(MenuRol obj) throws DataException;
}
