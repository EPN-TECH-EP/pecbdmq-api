package epntech.cbdmq.pe.servicio.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProInscripcion;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProInscripcionDto;

import java.util.List;
import java.util.Optional;

public interface ProInscripcionService extends ProfesionalizacionService<ProInscripcion, Integer>{
    Optional<ProInscripcionDto> getProDatosInscripcion(Integer codInscripcion);

    List<ProInscripcionDto> findByCodConvocatoria(Integer codConvocatoria);

    List<ProInscripcionDto> findByAceptado();
}
