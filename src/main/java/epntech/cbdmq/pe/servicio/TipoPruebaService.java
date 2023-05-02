/*
 * Copyright 2008-2023. Todos los derechos reservados.
 */
package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.TipoPrueba;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

/**
 * @author EPN TECH
 * @version $Revision: $
 */
public interface TipoPruebaService {
    TipoPrueba save(TipoPrueba obj) throws DataException;

    List<TipoPrueba> getAll();

    Optional<TipoPrueba> getById(Integer codigo);

    TipoPrueba update(TipoPrueba objActualizado) throws DataException;

    void delete(Integer codigo);
}
