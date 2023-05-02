/*
 * Copyright 2008-2023. Todos los derechos reservados.
 */
package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.TipoNota;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

/**
 * @author EPN TECH
 * @version $Revision: $
 */
public interface TipoNotaService {
    TipoNota save(TipoNota obj) throws DataException;

    List<TipoNota> getAll();

    Optional<TipoNota> getById(Integer codigo);

    TipoNota update(TipoNota objActualizado) throws DataException;

    void delete(Integer codigo);
}
