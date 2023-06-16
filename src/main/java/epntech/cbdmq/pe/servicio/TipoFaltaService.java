/*
 * Copyright 2008-2023. Todos los derechos reservados.
 */
package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.TipoFalta;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface TipoFaltaService {
    TipoFalta save(TipoFalta obj) throws DataException;

    List<TipoFalta> getAll();

    Optional<TipoFalta> getById(Integer codigo);

    TipoFalta update(TipoFalta objActualizado) throws DataException;

    void delete(Integer codigo);
}
