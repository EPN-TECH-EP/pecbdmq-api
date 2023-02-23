/*
 * Copyright 2008-2023. Todos los derechos reservados.
 */
package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.TipoFecha;

/**
 * @author EPN TECH
 * @version $Revision: $
 */
public interface TipoFechaRepository extends JpaRepository<TipoFecha, String> {
    Optional<TipoFecha> findByFecha(String fecha);
}
