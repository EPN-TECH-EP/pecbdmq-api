package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Modulo;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface ModuloService {
    Modulo save(Modulo obj) throws DataException;

    List<Modulo> getAll();

    Optional<Modulo> getById(Integer codigo);

    Modulo update(Modulo objActualizado) throws DataException;

    void delete(Integer codigo);
}
