package epntech.cbdmq.pe.resource;

import epntech.cbdmq.pe.dominio.Parametro;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.ParametroRepository;
import epntech.cbdmq.pe.resource.profesionalizacion.ProfesionalizacionResource;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ParametroServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parametro")
public class ParametroResource extends ProfesionalizacionResource<Parametro, Long, ParametroRepository, ParametroServiceImpl> {
    public ParametroResource(ParametroServiceImpl service) {
        super(service);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatos(@PathVariable("id") Long codigo, @RequestBody Parametro obj) throws DataException {
        return service.findById(codigo).map(datosGuardados-> {
            datosGuardados.setNombreParametro(obj.getNombreParametro());
            datosGuardados.setValor(obj.getValor());
            datosGuardados.setEstado(obj.getEstado());
            return super.actualizarDatos(datosGuardados);
        }).orElseGet(()->ResponseEntity.notFound().build());
    }
}
