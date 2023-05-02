package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Baja;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface BajaService {
	Baja save(Baja obj) throws DataException;

    List<Baja> getAll();

    Optional<Baja> getById(Integer codigo);

    Baja update(Baja objActualizado)throws DataException;

    void delete(Integer codigo);
}
