/*
 * Copyright 2008-2023. Todos los derechos reservados.
 */
package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.ComponenteNota;
import epntech.cbdmq.pe.dominio.admin.PonderacionModulos;
import epntech.cbdmq.pe.dominio.util.ComponenteTipo;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

/**
 * @author EPN TECH
 * @version $Revision: $
 */
public interface ComponenteNotaService {
    ComponenteNota save(ComponenteNota obj) throws DataException;

    List<ComponenteNota> getAll();
    List<ComponenteNota> getAllByCodPA();

    Optional<ComponenteNota> getById(int id);

    ComponenteNota update(ComponenteNota objActualizado)throws DataException;

    void delete(int id);
    
}
