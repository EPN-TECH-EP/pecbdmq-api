package epntech.cbdmq.pe.servicio.profesionalizacion;

import epntech.cbdmq.pe.excepcion.dominio.DataException;

import java.util.List;
import java.util.Optional;

public interface ProfesionalizacionService<T, U> {
    List<T> getAll();
    Optional<T> findById (U codigo);
    T save(T obj) throws DataException;
    T update(T datosGuardados) throws DataException;
    void delete(U codigo) throws DataException;
}
