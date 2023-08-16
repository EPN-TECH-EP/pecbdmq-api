package epntech.cbdmq.pe.resource.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProConvocatoriaRequisito;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProEtapas;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProConvocatoriaRequisitoDto;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.profesionalizacion.ProConvocatoriaRequisitoRepository;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ProConvocatoriaRequisitoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proConvocatoriaRequisito")
public class ProConvocatoriaRequisitoResource extends ProfesionalizacionResource<ProConvocatoriaRequisito, Integer, ProConvocatoriaRequisitoRepository, ProConvocatoriaRequisitoServiceImpl> {


    public ProConvocatoriaRequisitoResource(ProConvocatoriaRequisitoServiceImpl service) {
        super(service);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody ProConvocatoriaRequisito obj) throws DataException {
        return (ResponseEntity<ProEtapas>) service.findById(codigo).map(datosGuardados -> {
            datosGuardados.setCodigoRequisito(obj.getCodigoRequisito());
            datosGuardados.setEstado(obj.getEstado());
            datosGuardados.setCodigoConvocatoria(obj.getCodigoConvocatoria());
            return super.actualizarDatos(datosGuardados);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/datos/{id}")
    public List<ProConvocatoriaRequisitoDto> findRequisitosByConvocatoria(@PathVariable("id") Integer codConvocatoria){
        return service.findRequisitosByConvocatoria(codConvocatoria);
    }
}
