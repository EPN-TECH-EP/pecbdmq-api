package epntech.cbdmq.pe.resource.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProTipoProyecto;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.profesionalizacion.ProTipoProyectoRepository;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ProTipoProyectoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proTipoProyecto")
public class ProTipoProyectoResource extends ProfesionalizacionResource<ProTipoProyecto, Integer, ProTipoProyectoRepository, ProTipoProyectoServiceImpl>{
    public ProTipoProyectoResource(ProTipoProyectoServiceImpl service){
        super(service);
        }
@PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody ProTipoProyecto obj)throws DataException{
        return (ResponseEntity<ProTipoProyecto>) service.findById(codigo).map(datosGuardados->{
            datosGuardados.setNombreTipo(obj.getNombreTipo());
            datosGuardados.setEstado(obj.getEstado());
            return super.actualizarDatos(datosGuardados);
        }).orElseGet(()-> ResponseEntity.notFound().build());
}
}
