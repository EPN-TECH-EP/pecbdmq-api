/*
 * Copyright 2008-2023. Todos los derechos reservados.
 */
package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.ComponenteNota;

/**
 * @author EPN TECH
 * @version $Revision: $
 */
public interface ComponenteNotaRepository extends JpaRepository<ComponenteNota, Integer> {
    Optional<ComponenteNota> findByComponentenota(String componentenota);
}
