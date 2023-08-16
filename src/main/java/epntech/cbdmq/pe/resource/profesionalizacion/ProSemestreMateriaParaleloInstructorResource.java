package epntech.cbdmq.pe.resource.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProSemestreMateriaParaleloInstructor;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProParaleloInstructorDto;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProEstudianteSemestreMateriaParaleloInstructorRepository;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ProSemestreMateriaParaleloInstructorImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proSemestreMateriaParaleloInstructor")
public class ProSemestreMateriaParaleloInstructorResource extends ProfesionalizacionResource<ProSemestreMateriaParaleloInstructor, Integer, ProEstudianteSemestreMateriaParaleloInstructorRepository, ProSemestreMateriaParaleloInstructorImpl> {
    public ProSemestreMateriaParaleloInstructorResource(ProSemestreMateriaParaleloInstructorImpl service){
        super(service);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody ProSemestreMateriaParaleloInstructor obj) throws DataException{
        return (ResponseEntity<ProSemestreMateriaParaleloInstructor>) service.findById(codigo).map(datosGuardados -> {
            datosGuardados.setCodPeriodoSemestreMateriaParalelo(obj.getCodPeriodoSemestreMateriaParalelo());
            datosGuardados.setCodInstructor(obj.getCodInstructor());
            datosGuardados.setEstado(obj.getEstado());
            return super.actualizarDatos(datosGuardados);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/listar")
    public List<ProParaleloInstructorDto> getAllByCodMateriaParalelo(@PathVariable("id") Integer codigo){
        return service.getAllByCodMateriaParalelo(codigo);
    }
}
