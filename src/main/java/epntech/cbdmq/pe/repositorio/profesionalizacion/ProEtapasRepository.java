package epntech.cbdmq.pe.repositorio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProEtapas;

import java.util.Optional;

public interface ProEtapasRepository extends ProfesionalizacionRepository<ProEtapas, Integer> {

    Optional<ProEtapas> findByNombreEtapaIgnoreCase(String nombreEtapa);
}
