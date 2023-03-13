package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Notas;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface NotaService {

	Notas save(Notas obj) throws DataException;

    List<Notas> getAll();
    
    Optional<Notas> getById(Integer codigo);

    Notas update(Notas objActualizado)throws DataException;

    void delete(Integer codigo) throws DataException;
	
	
	
}
