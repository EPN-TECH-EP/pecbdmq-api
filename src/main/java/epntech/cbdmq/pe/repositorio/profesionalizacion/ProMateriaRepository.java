package epntech.cbdmq.pe.repositorio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProMateria;

import java.util.Optional;

public interface ProMateriaRepository extends ProfesionalizacionRepository<ProMateria, Integer> {

    Optional<ProMateria> findByNombreIgnoreCase(String nombre);
}
