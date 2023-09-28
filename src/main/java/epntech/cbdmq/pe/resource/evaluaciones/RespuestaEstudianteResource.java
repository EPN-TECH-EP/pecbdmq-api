package epntech.cbdmq.pe.resource.evaluaciones;

import epntech.cbdmq.pe.dominio.evaluaciones.RespuestaEstudiante;
import epntech.cbdmq.pe.servicio.impl.evaluaciones.RespuestaEstudianteServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/respuestaEstudiante")
public class RespuestaEstudianteResource {

    RespuestaEstudianteServiceImpl respuestaEstudianteServiceImpl;

    public RespuestaEstudianteResource(RespuestaEstudianteServiceImpl respuestaEstudianteServiceImpl) {
        this.respuestaEstudianteServiceImpl = respuestaEstudianteServiceImpl;
    }

    @GetMapping
    public List<RespuestaEstudiante> getRespuestaEstudiante() {
        return respuestaEstudianteServiceImpl.getAll();
    }

    @PostMapping
    public List<RespuestaEstudiante> saveRespuestaEstudiante(@RequestBody List<RespuestaEstudiante> respuestasEstudiante) {
        return respuestaEstudianteServiceImpl.saveAllRespuestas(respuestasEstudiante);

    }

}
