/*
 * Copyright 2008-2023. Todos los derechos reservados.
 */
package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.TipoBaja;

/**
 * @author EPN TECH
 * @version $Revision: $
 */
public interface TipoBajaService {
    TipoBaja save(TipoBaja obj);

    List<TipoBaja> getAll();

    Optional<TipoBaja> getById(Integer codigo);

    TipoBaja update(TipoBaja objActualizado);

    void delete(Integer codigo);
}
