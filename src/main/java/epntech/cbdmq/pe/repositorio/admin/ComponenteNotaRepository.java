/*
 * Copyright 2008-2023. Todos los derechos reservados.
 */
package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.ComponenteNota;


public interface ComponenteNotaRepository extends JpaRepository<ComponenteNota, Integer> {
    Optional<ComponenteNota> findByNombreIgnoreCaseAndCodPeriodoAcademico(String Nombre, Integer codPeriodoAcademico);
    List<ComponenteNota> findComponenteNotaByCodPeriodoAcademico(Integer codPeriodoAcademico);
}
