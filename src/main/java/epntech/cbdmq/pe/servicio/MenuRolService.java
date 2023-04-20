package epntech.cbdmq.pe.servicio;

import java.util.List;

import epntech.cbdmq.pe.dominio.admin.MenuRol;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface MenuRolService {

	List<MenuRol> getAll();

	List<MenuRol> getAllByRol(Long rol);

	MenuRol save(MenuRol obj) throws DataException;

	MenuRol update(MenuRol objActualizado) throws DataException;

	void delete(MenuRol obj) throws DataException;

	void deleteAllByMenuRolId_CodRol(Long codRol);

	List<MenuRol> saveAll(Iterable<MenuRol> entities);
	
	void deleteAndSave(Iterable<MenuRol> entities);
}
