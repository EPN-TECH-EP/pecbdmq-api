package epntech.cbdmq.pe.repositorio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProNotaProfesionalizacionGeneral;

import java.util.Optional;

public interface ProNotaProfesionalizacionGeneralRepository extends ProfesionalizacionRepository<ProNotaProfesionalizacionGeneral, Integer> {

    Optional<ProNotaProfesionalizacionGeneral> findByCodMateriaParalelo(Integer codMateriaParalelo);
}
