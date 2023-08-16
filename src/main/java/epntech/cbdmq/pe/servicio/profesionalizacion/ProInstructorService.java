package epntech.cbdmq.pe.servicio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProInstructor;
import epntech.cbdmq.pe.dominio.util.InstructorDatos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

import java.util.List;
import java.util.Optional;

public interface ProInstructorService {

    List<InstructorDatos> getAll();

    Optional<ProInstructor> getById(int id);

    ProInstructor save(ProInstructor obj) throws DataException;

    ProInstructor update(ProInstructor objActualizado) throws DataException;

    void delete(Integer codInstructor) throws DataException;
}
