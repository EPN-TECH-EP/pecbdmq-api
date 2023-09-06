package epntech.cbdmq.pe.resource.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProParalelo;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProParaleloRepository;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ProParaleloServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proParalelo")
public class ProParaleloResource extends ProfesionalizacionResource<ProParalelo, Integer, ProParaleloRepository, ProParaleloServiceImpl> {
    public ProParaleloResource(ProParaleloServiceImpl service){
        super(service);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody ProParalelo obj) throws DataException{
        return (ResponseEntity<ProParalelo>) service.findById(codigo).map(datosGuardados -> {
            datosGuardados.setNombreParalelo(obj.getNombreParalelo());
            datosGuardados.setEstado(obj.getEstado());
            return super.actualizarDatos(datosGuardados);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
