package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.SubTipoPrueba;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface SubtipoPruebaService {

	
	SubTipoPrueba save(SubTipoPrueba obj)throws DataException;
	
	List<SubTipoPrueba>getAll();
	
	Optional<SubTipoPrueba> getById(int id);
	
	SubTipoPrueba update(SubTipoPrueba objActualizado) throws DataException;

	void delete(int id) throws DataException;
	
}
