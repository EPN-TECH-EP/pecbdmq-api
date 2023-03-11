package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Parroquia;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface ParroquiaService {
	
	List<Parroquia> getAll();
	
	Optional<Parroquia> getById(int id);
	
	List<Parroquia> getAllByCodCantonId(int id);
}
