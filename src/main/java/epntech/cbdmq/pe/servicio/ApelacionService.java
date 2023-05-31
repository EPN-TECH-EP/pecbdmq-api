package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import java.text.ParseException;

import epntech.cbdmq.pe.dominio.admin.Apelacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface ApelacionService {

	Apelacion save(Apelacion obj) throws DataException, ParseException;

    List<Apelacion> getAll();

    Optional<Apelacion> getById(Integer codigo);

    Apelacion update(Apelacion objActualizado) throws DataException;

    void delete(Integer codigo);
    
    List<Apelacion> getByEstudiante(Integer codigo);
	
    List<Apelacion> getByInstructor(Integer codigo);
    
    Boolean validaFechaApelacion(Integer codNotaFormacion) throws DataException;
}
