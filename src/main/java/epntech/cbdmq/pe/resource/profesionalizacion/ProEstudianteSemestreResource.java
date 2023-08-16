package epntech.cbdmq.pe.resource.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProEstudianteSemestre;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.profesionalizacion.ProEstudianteSemestreRepository;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ProEstudianteSemestreServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proEstudianteSemestre")
public class ProEstudianteSemestreResource extends ProfesionalizacionResource<ProEstudianteSemestre, Integer, ProEstudianteSemestreRepository,ProEstudianteSemestreServiceImpl> {

    public ProEstudianteSemestreResource(ProEstudianteSemestreServiceImpl service) {
        super(service);
    }

    @GetMapping("/{id}/listar")
    public List<ProEstudianteSemestre> listar(@PathVariable("id") int codigo){return service.getBySemestre(codigo);}

    @PutMapping("/{id}")
    public ResponseEntity<ProEstudianteSemestre> actualizarDatos(@PathVariable("id") int codigo, @RequestBody ProEstudianteSemestre obj) throws DataException{
        return (ResponseEntity<ProEstudianteSemestre>) service.getById(codigo).map(datosGuardados->{
            datosGuardados.setCodigoSemestre((int)obj.getCodigoSemestre().doubleValue()) ;
            datosGuardados.setCodigoSemestre((int) obj.getCodigoSemestre().doubleValue());
            datosGuardados.setEstado(obj.getEstado());
            return super.actualizarDatos(datosGuardados);
        }).orElseGet(()->ResponseEntity.notFound().build());
    }

}
