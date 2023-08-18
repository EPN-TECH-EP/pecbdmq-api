package epntech.cbdmq.pe.resource.profesionalizacion;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProPeriodos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProPeriodosRepository;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ProPeriodoServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proPeriodos")
public class ProPeriodosResource extends ProfesionalizacionResource<ProPeriodos, Integer, ProPeriodosRepository, ProPeriodoServiceImpl> {
    public ProPeriodosResource(ProPeriodoServiceImpl service){
        super(service);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody ProPeriodos obj) throws DataException{
        return service.findById(codigo).map(datosGuardados -> {
            datosGuardados.setNombrePeriodo(obj.getNombrePeriodo());
            datosGuardados.setFechaInicio(obj.getFechaInicio());
            datosGuardados.setFechaFin(obj.getFechaFin());
            datosGuardados.setEstado(obj.getEstado());
            return super.actualizarDatos(datosGuardados);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{estado}")
    public List<ProPeriodos> findByEstado(@PathVariable("estado") String estado){
        return service.findByEstado(estado);
    }
}
