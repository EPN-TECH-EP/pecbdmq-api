package epntech.cbdmq.pe.servicio.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProTipoProyecto;

import java.util.Optional;

public interface ProTipoProyectoService extends ProfesionalizacionService<ProTipoProyecto, Integer>{
    Optional<ProTipoProyecto> findByNombre(String nombreTipo);
}
