package epntech.cbdmq.pe.resource.fichaPersonal;

import epntech.cbdmq.pe.dominio.fichaPersonal.especializacion.EspecializacionEstudiante;
import epntech.cbdmq.pe.dominio.fichaPersonal.especializacion.EspecializacionInstructor;
import epntech.cbdmq.pe.servicio.fichaPersonal.fichaPersonalImpl.EspecializacionHistoricoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/historicoEsp")
public class EspecializacionHistoricoResource {

    @Autowired
    private EspecializacionHistoricoServiceImpl objService;

    @PostMapping("/estudiante")
    public List<EspecializacionEstudiante> listarEspHistorico(@RequestParam("codUnico") String codEstudiante, Pageable pageable) {
        return objService.getEspecializacionHistoricos(codEstudiante, pageable);
    }

    @PostMapping("/instructor")
    public List<EspecializacionInstructor> listarEspHistorico(@RequestParam("codInstructor") Integer codInstructor, Pageable pageable) {
        return objService.getHistoricoInstructor(codInstructor, pageable);
    }


}
