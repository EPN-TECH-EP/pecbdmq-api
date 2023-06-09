package epntech.cbdmq.pe.resource.fichaPersonal;

import epntech.cbdmq.pe.dominio.fichaPersonal.profesionalizacion.ProfesionalizacionEstudiante;
import epntech.cbdmq.pe.dominio.fichaPersonal.profesionalizacion.ProfesionalizacionInstructor;
import epntech.cbdmq.pe.servicio.fichaPersonal.fichaPersonalImpl.ProfesionalizacionHistoricoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/historicoPro")
public class ProfesionalizacionHistoricoResource {

    @Autowired
    private ProfesionalizacionHistoricoServiceImpl objService;

    @PostMapping("/estudiante")
    public List<ProfesionalizacionEstudiante> listarProfHistorico(@RequestParam("codUnico") String codEstudiante, Pageable pageable) {
        return objService.getProfesionalizacionHistoricos(codEstudiante, pageable);
    }

    @PostMapping("/instructor")
    public List<ProfesionalizacionInstructor> listarProfHistorico(@RequestParam("codInstructor") Integer codInstructor, Pageable pageable) {
        return objService.getProfesionalizacionHistoricos(codInstructor, pageable);
    }

}
