package epntech.cbdmq.pe.repositorio.admin.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProCatalogoProyecto;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProfesionalizacionRepository;

import java.util.Optional;

public interface ProCatalogoProyectoRepository extends ProfesionalizacionRepository<ProCatalogoProyecto, Integer> {
    @Override
    Optional<ProCatalogoProyecto> findById(Integer Codigo);
    Optional<ProCatalogoProyecto> findByNombreCatalogo(String nombreCatalogo);
}
