package epntech.cbdmq.pe.repositorio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProInscripcionesDelegados;

import java.util.Optional;

public interface ProInscripcionesDelegadosRepository  extends ProfesionalizacionRepository<ProInscripcionesDelegados, Integer> {

    Optional<ProInscripcionesDelegados> findByCodInscripciones(int codInscripciones);
    Optional<ProInscripcionesDelegados> findByCodInscripcionesAndCodDelegados(int codInscripciones, int codDelegado);

}
