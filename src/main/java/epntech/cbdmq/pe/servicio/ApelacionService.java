package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Apelacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface ApelacionService {

	Apelacion save(Apelacion obj) throws DataException;

    List<Apelacion> getAll();

    Optional<Apelacion> getById(Integer codigo);

    Apelacion update(Apelacion objActualizado) throws DataException;

    void delete(Integer codigo);
	
	
}
