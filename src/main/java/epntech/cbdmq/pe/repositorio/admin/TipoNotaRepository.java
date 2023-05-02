/*
 * Copyright 2008-2023. Todos los derechos reservados.
 */
package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.TipoNota;

/**
 * @author EPN TECH
 * @version $Revision: $
 */
public interface TipoNotaRepository extends JpaRepository<TipoNota, Integer> {
    
   
    Optional<TipoNota> findByNotaIgnoreCase(String nota);
}
