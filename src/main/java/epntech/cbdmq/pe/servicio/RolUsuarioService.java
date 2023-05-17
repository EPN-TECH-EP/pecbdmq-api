package epntech.cbdmq.pe.servicio;

import java.util.List;

import epntech.cbdmq.pe.dominio.admin.RolUsuario;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface RolUsuarioService {

	List<RolUsuario> getAll();

	List<RolUsuario> getAllByUsuario(Long codUsuario);
	
	List<RolUsuario> getAllByRol(Long codRol);

	RolUsuario save(RolUsuario obj) throws DataException;

	RolUsuario update(RolUsuario objActualizado) throws DataException;

	void delete(RolUsuario obj) throws DataException;

	void deleteAllByRolUsuarioId_codUsuario(Long codUsuario);

	List<RolUsuario> saveAll(Iterable<RolUsuario> entities);
	
	void deleteAndSave(Iterable<RolUsuario> entities, Long codUsuario);
}
