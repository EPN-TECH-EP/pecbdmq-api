package epntech.cbdmq.pe.servicio.especializacion;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.especializacion.CursoPrueba;

public interface CursoPruebaService {

	CursoPrueba save(CursoPrueba cursoPrueba);
	
	CursoPrueba update(CursoPrueba cursoPruebaActualizado);
	
	List<CursoPrueba> listAll();
	
	Optional<CursoPrueba> getById(Long codCursoPruebaIngreso);
	
	
}
