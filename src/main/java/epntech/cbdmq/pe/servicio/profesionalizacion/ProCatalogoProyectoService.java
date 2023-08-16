package epntech.cbdmq.pe.servicio.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProCatalogoProyecto;

import java.util.Optional;

public interface ProCatalogoProyectoService extends ProfesionalizacionService<ProCatalogoProyecto, Integer>{
    Optional<ProCatalogoProyecto> findByNombreCatalogo(String nombreCatalogoProyecto);
}
