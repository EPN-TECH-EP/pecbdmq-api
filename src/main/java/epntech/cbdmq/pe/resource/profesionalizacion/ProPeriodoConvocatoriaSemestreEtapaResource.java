package epntech.cbdmq.pe.resource.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProPeriodoConvocatoriaSemestreEtapa;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProPeriodoConvocatoriaSemestreEtapaRepository;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ProPeriodoConvocatoriaSemestreEtapaServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proeriodoConvocatoriaSemestreEtapa")
public class ProPeriodoConvocatoriaSemestreEtapaResource extends ProfesionalizacionResource<ProPeriodoConvocatoriaSemestreEtapa, Integer, ProPeriodoConvocatoriaSemestreEtapaRepository, ProPeriodoConvocatoriaSemestreEtapaServiceImpl> {
    public  ProPeriodoConvocatoriaSemestreEtapaResource(ProPeriodoConvocatoriaSemestreEtapaServiceImpl service){
        super(service);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody ProPeriodoConvocatoriaSemestreEtapa obj) throws DataException{
        return (ResponseEntity<ProPeriodoConvocatoriaSemestreEtapa>) service.findById(codigo).map(datosGuardados -> {

            datosGuardados.setCodPeriodo(obj.getCodPeriodo());
            datosGuardados.setCodConvocatoria(obj.getCodConvocatoria());
            datosGuardados.setCodSemestre(obj.getCodSemestre());
            datosGuardados.setCodEtapa(obj.getCodEtapa());
            datosGuardados.setEstado(obj.getEstado());
            return super.actualizarDatos(datosGuardados);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
