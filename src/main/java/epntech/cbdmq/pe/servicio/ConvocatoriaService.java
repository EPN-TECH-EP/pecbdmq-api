package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import epntech.cbdmq.pe.dominio.admin.Convocatoria;

import epntech.cbdmq.pe.dominio.admin.Convocatorialistar;

import epntech.cbdmq.pe.excepcion.dominio.DataException;


public interface ConvocatoriaService {

	Convocatoria saveData(Convocatoria obj) throws DataException;
	
	List<Convocatoria> getAllData();
	
	Optional<Convocatoria> getByIdData(int id);
	
	Convocatoria updateData(Convocatoria objActualizado)throws DataException ;
	
	void deleteData(int id) throws DataException;
	

	List<Convocatorialistar> getConvocatorialistar();
	
	Set<Convocatoria> getConvocatoriaActiva();

}
