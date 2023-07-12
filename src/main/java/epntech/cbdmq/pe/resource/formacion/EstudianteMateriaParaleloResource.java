package epntech.cbdmq.pe.resource.formacion;

import epntech.cbdmq.pe.dominio.admin.formacion.EstudianteMateriaParalelo;
import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;
import epntech.cbdmq.pe.dominio.util.EstudianteParaleloDto;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.formacion.EstudianteMateriaParaleloService;
import epntech.cbdmq.pe.servicio.formacion.InstructorMateriaParaleloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudianteMateriaParalelo")
public class EstudianteMateriaParaleloResource {

    @Autowired
    EstudianteMateriaParaleloService objService;
    @GetMapping("/listar")
    public List<EstudianteMateriaParalelo> listar() throws DataException {
        return objService.getEstudiantesMateriaParalelo();
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EstudianteMateriaParalelo> guardar(@RequestBody EstudianteMateriaParalelo obj) throws DataException {
        return new ResponseEntity<EstudianteMateriaParalelo>(objService.save(obj), HttpStatus.OK);
    }
    @PostMapping("/asignar")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Boolean> asignar(@RequestBody EstudianteParaleloDto obj) throws DataException {
        return new ResponseEntity<>(objService.asignarEstudiantesParalelo(obj.getLista(),obj.getCodParalelo()), HttpStatus.OK);
    }
}
