package epntech.cbdmq.pe.servicio.evaluaciones;

import epntech.cbdmq.pe.dominio.evaluaciones.TipoEvaluacion;

import java.util.List;

public interface TipoEvaluacionService {

    List<TipoEvaluacion> getAll();

    TipoEvaluacion getById(Long codTipoEvaluacion);

    TipoEvaluacion save(TipoEvaluacion tipoEvaluacion);

    TipoEvaluacion update(Long codTipoEvaluacion, TipoEvaluacion tipoEvaluacionActualizado);

    void delete(Long codTipoEvaluacion);

}
