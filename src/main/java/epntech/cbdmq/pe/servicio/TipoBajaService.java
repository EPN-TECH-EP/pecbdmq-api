/*
 * Copyright 2008-2023. Todos los derechos reservados.
 */
package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.TipoBaja;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

/**
 * @author EPN TECH
 * @version $Revision: $
 */
public interface TipoBajaService {
    TipoBaja save(TipoBaja obj) throws DataException;

    List<TipoBaja> getAll();

    Optional<TipoBaja> getById(Integer codigo);

    TipoBaja update(TipoBaja objActualizado) throws DataException ;

    void delete(Integer codigo);
}
