/*
 * Copyright 2008-2023. Todos los derechos reservados.
 */
package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.TipoBaja;

/**
 * @author EPN TECH
 * @version $Revision: $
 */
public interface TipoBajaRepository extends JpaRepository<TipoBaja, Integer> {
    Optional<TipoBaja> findByBajaIgnoreCase(String baja);
}
