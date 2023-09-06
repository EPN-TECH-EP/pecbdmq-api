package epntech.cbdmq.pe.resource.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.TipoContrato;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.profesionalizacion.TipoContratoRepository;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.TipoContratoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tipoContrato")
public class TipoContratoResource extends ProfesionalizacionResource<TipoContrato, Integer, TipoContratoRepository, TipoContratoServiceImpl> {
    public TipoContratoResource(TipoContratoServiceImpl service){
        super(service);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatos(@PathVariable ("id") int codigo, @RequestBody TipoContrato obj) throws DataException{
        return (ResponseEntity<TipoContrato>) service.findById(codigo).map(datosGuardados-> {
            datosGuardados.setNombre(obj.getNombre().toUpperCase());
            datosGuardados.setEstado(obj.getEstado());
            return super.actualizarDatos(datosGuardados);
        }).orElseGet(()->ResponseEntity.notFound().build());

    }
}
