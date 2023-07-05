package epntech.cbdmq.pe.resource.fichaPersonal;

import epntech.cbdmq.pe.dominio.admin.MateriaPeriodo;
import epntech.cbdmq.pe.dominio.fichaPersonal.formacion.FormacionEstudiante;
import epntech.cbdmq.pe.dominio.fichaPersonal.formacion.FormacionInstructor;
import epntech.cbdmq.pe.servicio.fichaPersonal.fichaPersonalImpl.FormacionHistoricoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/historicoFor")
public class FormacionHistoricoResource {
    @Autowired
    private FormacionHistoricoServiceImpl objService;

    @PostMapping("/estudiante")
    public List<FormacionEstudiante> listarFormHistorico(@RequestParam("codUnico") String codEstudiante, Pageable pageable) {
        return objService.getHistoricos(codEstudiante, pageable);
    }

    @PostMapping("/instructor")
    public List<FormacionInstructor> listarFormHistorico(@RequestParam("codInstructor") Integer codInstructor, Pageable pageable) {
        return objService.getFormHistoricos(codInstructor, pageable);
    }
    @GetMapping("/materias")
    public List<?> obtenerAllMaterias() {
        return objService.listaMateriasHistorico();
    }
    @PostMapping("/materiaPeriodo")
    public Optional<MateriaPeriodo> getMateriaPeriodo(@RequestParam("periodo")String nombrePeriodo, @RequestParam("materia")String nombreMateria, Pageable pageable) {
        return objService.getMateriaByNombrePeriodo(nombreMateria,nombrePeriodo);
    }
}
