package epntech.cbdmq.pe.servicio.impl.evaluaciones;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.evaluaciones.TipoEvaluacion;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import epntech.cbdmq.pe.repositorio.evaluaciones.TipoEvaluacionRepository;
import epntech.cbdmq.pe.servicio.evaluaciones.TipoEvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;


import java.util.List;
import java.util.Optional;

@Service
public class TipoEvaluacionServiceImpl implements TipoEvaluacionService {

    @Autowired
    TipoEvaluacionRepository tipoEvaluacionRepository;


    @Override
    public List<TipoEvaluacion> getAll() {
        return tipoEvaluacionRepository.findByEstadoIgnoreCase(EstadosConst.ACTIVO);
    }

    @Override
    public TipoEvaluacion getById(Long codTipoEvaluacion) {
        return tipoEvaluacionRepository.findById(codTipoEvaluacion).orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));
    }

    @Override
    public TipoEvaluacion save(TipoEvaluacion tipoEvaluacion) {

        Optional<TipoEvaluacion> tipoEvaluacionGuardado = tipoEvaluacionRepository.findByNombreIgnoreCase(tipoEvaluacion.getNombre());

        if (tipoEvaluacionGuardado.isPresent()) {
            TipoEvaluacion tipoEvaluacionExistente = tipoEvaluacionGuardado.get();

            if (EstadosConst.ELIMINADO.equalsIgnoreCase(tipoEvaluacionExistente.getEstado())) {
                tipoEvaluacionExistente.setEstado(EstadosConst.ACTIVO);
            } else {
                throw new BusinessException(REGISTRO_YA_EXISTE);
            }
        }

        return tipoEvaluacionRepository.save(tipoEvaluacion);
    }

    @Override
    public TipoEvaluacion update(Long codTipoEvaluacion, TipoEvaluacion tipoEvaluacionActualizado) {

        TipoEvaluacion tipoEvaluacion = tipoEvaluacionRepository.findById(codTipoEvaluacion)
                .orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

        tipoEvaluacion.setNombre(tipoEvaluacionActualizado.getNombre());
        tipoEvaluacion.setEstado(tipoEvaluacionActualizado.getEstado());

        return tipoEvaluacionRepository.save(tipoEvaluacion);

    }

    @Override
    public void delete(Long codTipoEvaluacion) {

        tipoEvaluacionRepository.findById(codTipoEvaluacion)
                .orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

        tipoEvaluacionRepository.deleteById(codTipoEvaluacion);

    }
}
