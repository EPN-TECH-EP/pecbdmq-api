/*
 * Copyright 2008-2023. Todos los derechos reservados.
 */
package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.TipoSancion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface TipoSancionService {
    TipoSancion save(TipoSancion obj) throws DataException;

    List<TipoSancion> getAll();

    Optional<TipoSancion> getById(Integer codigo);

    TipoSancion update(TipoSancion objActualizado);

    void delete(Integer codigo);
}
