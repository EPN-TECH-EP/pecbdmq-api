package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Rol;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface RolService {

	Rol save(Rol obj) throws DataException;

	List<Rol> getAll();

	Optional<Rol> getById(Long id);

	Rol update(Rol objActualizado) throws DataException;

	void delete(Long id) throws DataException;
}
