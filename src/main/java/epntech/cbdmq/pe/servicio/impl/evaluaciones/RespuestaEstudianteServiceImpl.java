package epntech.cbdmq.pe.servicio.impl.evaluaciones;

import epntech.cbdmq.pe.dominio.evaluaciones.RespuestaEstudiante;
import epntech.cbdmq.pe.repositorio.evaluaciones.RespuestaEstudianteRepository;
import epntech.cbdmq.pe.servicio.evaluaciones.RespuestaEstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RespuestaEstudianteServiceImpl implements RespuestaEstudianteService {

    @Autowired
    private RespuestaEstudianteRepository respuestaEstudianteRepository;

    @Override
    public List<RespuestaEstudiante> getAll() {
        return respuestaEstudianteRepository.findAll();
    }

    @Override
    public List<RespuestaEstudiante> getByCodEvaluacion(Long codEvaluacion) {
        return respuestaEstudianteRepository.findByCodEvaluacion(codEvaluacion);
    }

    @Override
    public List<RespuestaEstudiante> getByCodEstudiante(Long codEstudiante) {
        return respuestaEstudianteRepository.findByCodEstudiante(codEstudiante);
    }

    @Override
    public List<RespuestaEstudiante> getByCodEvaluacionAndCodEstudiante(Long codEvaluacion, Long codEstudiante) {
        return respuestaEstudianteRepository.findByCodEvaluacionAndCodEstudiante(codEvaluacion, codEstudiante);
    }

    @Override
    public RespuestaEstudiante getById(Long codRespuestaEstudiante) {
        return respuestaEstudianteRepository.findById(codRespuestaEstudiante).orElse(null);
    }

    @Override
    public List<RespuestaEstudiante> saveAllRespuestas(List<RespuestaEstudiante> respuestasEstudiante) {
        return respuestaEstudianteRepository.saveAll(respuestasEstudiante);
    }

    @Override
    public void delete(Long codRespuestaEstudiante) {
        respuestaEstudianteRepository.deleteById(codRespuestaEstudiante);
    }

    @Override
    public Boolean esEncuestaContestada(Long codEstudiante, Long codEvaluacion) {
        List<RespuestaEstudiante> respuestasEstudiante = respuestaEstudianteRepository.findByCodEstudianteAndCodEvaluacion(codEstudiante, codEvaluacion);
        return respuestasEstudiante.size() > 0;
    }
}
