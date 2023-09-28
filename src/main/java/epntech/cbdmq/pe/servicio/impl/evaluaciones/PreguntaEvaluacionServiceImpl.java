package epntech.cbdmq.pe.servicio.impl.evaluaciones;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.evaluaciones.PreguntaTipoEvaluacion;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import epntech.cbdmq.pe.repositorio.evaluaciones.PreguntaTipoEvaluacionRepository;
import epntech.cbdmq.pe.servicio.evaluaciones.PreguntaEvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;

@Service
public class PreguntaEvaluacionServiceImpl implements PreguntaEvaluacionService {

    @Autowired
    PreguntaTipoEvaluacionRepository preguntaTipoEvaluacionRepository;

    @Override
    public List<PreguntaTipoEvaluacion> getAll() {
        return preguntaTipoEvaluacionRepository.findByEstadoIgnoreCase(EstadosConst.ACTIVO);
    }

    @Override
    public List<PreguntaTipoEvaluacion> getByCodTipoEvaluacion(Long codTipoEvaluacion) {
        return preguntaTipoEvaluacionRepository.findByCodTipoEvaluacion(codTipoEvaluacion);
    }

    @Override
    public PreguntaTipoEvaluacion getById(Long codPreguntaEvaluacion) {
        return preguntaTipoEvaluacionRepository.findById(codPreguntaEvaluacion)
                .orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));
    }

    @Override
    public PreguntaTipoEvaluacion save(PreguntaTipoEvaluacion preguntaTipoEvaluacion) {

        Optional<PreguntaTipoEvaluacion> preguntaEvaluacionGuardado = preguntaTipoEvaluacionRepository.findByPreguntaIgnoreCase(preguntaTipoEvaluacion.getPregunta());

        if (preguntaEvaluacionGuardado.isPresent()) {
            PreguntaTipoEvaluacion preguntaTipoEvaluacionExistente = preguntaEvaluacionGuardado.get();

            if (EstadosConst.ELIMINADO.equalsIgnoreCase(preguntaTipoEvaluacionExistente.getEstado())) {
                preguntaTipoEvaluacionExistente.setEstado(EstadosConst.ACTIVO);
            } else {
                throw new BusinessException(REGISTRO_NO_EXISTE);
            }
        }

        return preguntaTipoEvaluacionRepository.save(preguntaTipoEvaluacion);

    }

    @Override
    public PreguntaTipoEvaluacion update(Long codPreguntaEvaluacion, PreguntaTipoEvaluacion preguntaTipoEvaluacionActualizado) {
        PreguntaTipoEvaluacion preguntaTipoEvaluacion = preguntaTipoEvaluacionRepository.findById(codPreguntaEvaluacion)
                .orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

        preguntaTipoEvaluacion.setPregunta(preguntaTipoEvaluacionActualizado.getPregunta());
        preguntaTipoEvaluacion.setCodTipoEvaluacion(preguntaTipoEvaluacionActualizado.getCodTipoEvaluacion());
        preguntaTipoEvaluacion.setEstado(preguntaTipoEvaluacionActualizado.getEstado());

        return preguntaTipoEvaluacionRepository.save(preguntaTipoEvaluacion);
    }

    @Override
    public void delete(Long codPreguntaEvaluacion) {

        preguntaTipoEvaluacionRepository.findById(codPreguntaEvaluacion)
                .orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

        preguntaTipoEvaluacionRepository.deleteById(codPreguntaEvaluacion);
    }
}
