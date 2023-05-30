package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.NO_ENCUENTRA;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;
import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_REGISTRADOS;

import java.util.List;

import epntech.cbdmq.pe.dominio.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Estudiante;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.EstudianteServiceImpl;

@RestController
@RequestMapping("/estudiante")
public class EstudianteResource {

    @Autowired
    private EstudianteServiceImpl objService;

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> guardar(@RequestBody Estudiante obj) throws DataException {
        return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
    }
    @PostMapping("/listarFormHistorico")
    public List<FormacionEstudiante> listarFormHistorico(@RequestParam("codUnico") String codEstudiante, Pageable pageable) {
        return objService.getHistoricos(codEstudiante, pageable);
    }

    @PostMapping("/listarEspHistorico")
    public List<EspecializacionEstudiante> listarEspHistorico(@RequestParam("codUnico") String codEstudiante, Pageable pageable) {
        return objService.getEspecializacionHistoricos(codEstudiante, pageable);
    }

    @PostMapping("/listarProfHistorico")
    public List<ProfesionalizacionEstudiante> listarProfHistorico(@RequestParam("codUnico") String codEstudiante, Pageable pageable) {
        return objService.getProfesionalizacionHistoricos(codEstudiante, pageable);
    }

    @PostMapping("/ByUser")
    public EstudianteDto listarEstudianteByUsuario(@RequestParam("codUsuario") String codUsuario) throws DataException {
        EstudianteDto estudiante = objService.getEstudianteByUsuario(codUsuario);
        System.out.println(estudiante);
        if (estudiante == null) {
            throw new DataException(NO_ENCUENTRA);
        }
        return estudiante;
    }
}


