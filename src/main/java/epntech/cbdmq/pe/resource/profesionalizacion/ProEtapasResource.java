package epntech.cbdmq.pe.resource.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProEtapas;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProEtapasRepository;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ProEtapaServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proEtapas")
public class ProEtapasResource extends ProfesionalizacionResource<ProEtapas, Integer, ProEtapasRepository, ProEtapaServiceImpl> {

    public ProEtapasResource(ProEtapaServiceImpl service){
        super(service);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody ProEtapas obj) throws DataException{
        return (ResponseEntity<ProEtapas>) service.findById(codigo).map(datosGuardados -> {
            datosGuardados.setNombreEtapa(obj.getNombreEtapa());
            datosGuardados.setEstado(obj.getEstado());
            return super.actualizarDatos(datosGuardados);
        }).orElseGet(()-> ResponseEntity.notFound().build());

    }

}
