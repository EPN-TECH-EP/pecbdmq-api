package epntech.cbdmq.pe.repositorio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProInstructor;

import java.util.Optional;

public interface ProInstructorRepository extends ProfesionalizacionRepository<ProInstructor, Integer> {


    Optional<ProInstructor> findByCodDatosPersonales(int codDatosPersonales);
}
