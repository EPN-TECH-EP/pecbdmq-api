package epntech.cbdmq.pe.servicio.evaluaciones;

import epntech.cbdmq.pe.dominio.evaluaciones.PreguntaTipoEvaluacion;

import java.util.List;

public interface PreguntaEvaluacionService {

    List<PreguntaTipoEvaluacion> getAll();

    List<PreguntaTipoEvaluacion> getByCodTipoEvaluacion(Long codTipoEvaluacion);
    List<PreguntaTipoEvaluacion> getByNombreTipEvaluacion(String nomnbreTipoEvaluacion);
    List<PreguntaTipoEvaluacion> getPreguntasToInstructor();

    PreguntaTipoEvaluacion getById(Long codPreguntaEvaluacion);

    PreguntaTipoEvaluacion save(PreguntaTipoEvaluacion preguntaTipoEvaluacion);

    PreguntaTipoEvaluacion update(Long codPreguntaEvaluacion, PreguntaTipoEvaluacion preguntaTipoEvaluacionActualizado);

    void delete(Long codPreguntaEvaluacion);

}
