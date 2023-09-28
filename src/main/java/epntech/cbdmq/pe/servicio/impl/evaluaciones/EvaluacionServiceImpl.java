package epntech.cbdmq.pe.servicio.impl.evaluaciones;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.evaluaciones.Evaluacion;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import epntech.cbdmq.pe.repositorio.evaluaciones.EvaluacionRepository;
import epntech.cbdmq.pe.servicio.evaluaciones.EvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

@Service
public class EvaluacionServiceImpl implements EvaluacionService {

    @Autowired
    EvaluacionRepository evaluacionServiceRepository;

    @Override
    public List<Evaluacion> getAll() {
        return evaluacionServiceRepository.findByEstadoIgnoreCase(EstadosConst.ACTIVO);
    }

    @Override
    public Evaluacion getById(Long codEvaluacion) {
        return evaluacionServiceRepository.findById(codEvaluacion).orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));
    }

    @Override
    public Evaluacion save(Evaluacion evaluacion) {

        Optional<Evaluacion> evaluacionGuardado = evaluacionServiceRepository.findByNombreIgnoreCase(evaluacion.getNombre());

        if (evaluacionGuardado.isPresent()) {
            Evaluacion evaluacionExistente = evaluacionGuardado.get();

            if (EstadosConst.ELIMINADO.equalsIgnoreCase(evaluacionExistente.getEstado())) {
                evaluacionExistente.setEstado(EstadosConst.ACTIVO);
            } else {
                throw new BusinessException(REGISTRO_YA_EXISTE);
            }
        }

        return evaluacionServiceRepository.save(evaluacion);

    }

    @Override
    public Evaluacion update(Long codEvaluacion, Evaluacion evaluacionActualizado) {

        Evaluacion evaluacion = evaluacionServiceRepository.findById(codEvaluacion)
                .orElseThrow(() -> new BusinessException(REGISTRO_YA_EXISTE));

        evaluacion.setNombre(evaluacionActualizado.getNombre());
        evaluacion.setAutor(evaluacionActualizado.getAutor());
        evaluacion.setFechaCreacion(evaluacionActualizado.getFechaCreacion());
        evaluacion.setCodTipoEvaluacion(evaluacionActualizado.getCodTipoEvaluacion());

        return evaluacionServiceRepository.save(evaluacion);


    }

    @Override
    public void delete(Long codEvaluacion) {

        evaluacionServiceRepository.findById(codEvaluacion)
                .orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

        evaluacionServiceRepository.deleteById(codEvaluacion);

    }
}
