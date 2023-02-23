/*
 * Copyright 2008-2023. Todos los derechos reservados.
 */
package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.TipoFecha;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

/**
 * @author EPN TECH
 * @version $Revision: $
 */
public interface TipoFechaService {
    TipoFecha save(TipoFecha obj) throws DataException;

    List<TipoFecha> getAll();

    Optional<TipoFecha> getById(String fecha);

    TipoFecha update(TipoFecha objActualizado);

    void delete(String fecha);
}
