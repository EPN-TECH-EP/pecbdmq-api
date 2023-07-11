
package epntech.cbdmq.pe.servicio.especializacion;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.especializacion.ModuloEspecializacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface ModuloEspecializacionService {

	ModuloEspecializacion save(ModuloEspecializacion moduloEspecializacion) throws DataException ;
	
	ModuloEspecializacion update(ModuloEspecializacion moduloEspecializacionActualizado) throws DataException ;
	
	List<ModuloEspecializacion> getAll();
	
	Optional<ModuloEspecializacion> getById(Long codModulo)  throws DataException;
	
	void delete(Long codModulo)  throws DataException;
}
