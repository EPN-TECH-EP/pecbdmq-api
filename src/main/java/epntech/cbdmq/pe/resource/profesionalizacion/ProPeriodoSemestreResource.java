package epntech.cbdmq.pe.resource.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProPeriodoSemestre;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProPeriodoSemestreDto;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProPeriodoSemestreRepository;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ProPeriodoSemestreServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proPeriodoSemestre")
public class ProPeriodoSemestreResource extends ProfesionalizacionResource<ProPeriodoSemestre,Integer, ProPeriodoSemestreRepository,ProPeriodoSemestreServiceImpl> {

    public ProPeriodoSemestreResource(ProPeriodoSemestreServiceImpl service) {
        super(service);
    }

    @GetMapping("/{id}/listar")
    public List<ProPeriodoSemestreDto> getAllByPeriodo(@PathVariable("id") Integer codigo){
        return service.getAllByPeriodo(codigo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody ProPeriodoSemestre obj) throws DataException{
        return (ResponseEntity<ProPeriodoSemestre>) service.findById(codigo).map(datosGuardados -> {
            datosGuardados.setCodSemestre(obj.getCodSemestre());
            datosGuardados.setCodPeriodo(obj.getCodPeriodo());
            datosGuardados.setEstado(obj.getEstado());
            return super.actualizarDatos(datosGuardados);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


}
