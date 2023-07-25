package epntech.cbdmq.pe.resource.formacion;

import epntech.cbdmq.pe.dominio.admin.formacion.*;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.formacion.InstructorMateriaParaleloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructorMateriaParalelo")
public class InstructorMateriaParaleloResource {
    @Autowired
    InstructorMateriaParaleloService objService;

    @GetMapping("/listar")
    public List<InstructorMateriaParalelo> listar() throws DataException {
        return objService.getInstructoresMateriaParalelo();
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<InstructorMateriaParalelo> guardar(@RequestBody InstructorMateriaParalelo obj) throws DataException {
        return new ResponseEntity<InstructorMateriaParalelo>(objService.save(obj), HttpStatus.OK);
    }

    @PostMapping("/asignar")
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean asignar(@RequestBody InstructorMateriaCreateDto objInstructorMateria) throws DataException {
        return objService.asignarInstructortoMateriaParalelo(objInstructorMateria.getCodMateria(),objInstructorMateria.getCodCoordinador(),objInstructorMateria.getCodAsistentes(),objInstructorMateria.getCodInstructores(), objInstructorMateria.getCodParalelo());
    }
    @PostMapping("/actualizarInstructores")
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean actualizar(@RequestBody InstructorMateriaCreateDto objInstructorMateria) throws DataException {
        return objService.actualizarInstructorMateriaParalelo(objInstructorMateria.getCodMateria(),objInstructorMateria.getCodCoordinador(),objInstructorMateria.getCodAsistentes(),objInstructorMateria.getCodInstructores(), objInstructorMateria.getCodParalelo());
    }
    @GetMapping("/listarMateriasGroupByParalelos")
    public InstructorMateriaParalelosDto getMateriaPAParaleloNombres() throws DataException {
        return objService.getMateriaPAParaleloNombres();
    }
}
