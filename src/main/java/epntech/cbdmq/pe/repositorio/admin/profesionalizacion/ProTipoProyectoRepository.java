package epntech.cbdmq.pe.repositorio.admin.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProTipoProyecto;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProfesionalizacionRepository;

import java.util.Optional;

public interface ProTipoProyectoRepository extends ProfesionalizacionRepository<ProTipoProyecto, Integer> {
   
    Optional<ProTipoProyecto> findByNombreTipo(String nombreTipo);
}
