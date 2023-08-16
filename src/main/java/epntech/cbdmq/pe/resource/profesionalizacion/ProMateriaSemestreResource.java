package epntech.cbdmq.pe.resource.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProMateriaSemestre;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProMateriaSemestreDto;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.profesionalizacion.ProMateriaSemestreRepository;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ProMateriaSemestreServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proMateriaSemestre")
public class ProMateriaSemestreResource extends ProfesionalizacionResource<ProMateriaSemestre, Integer, ProMateriaSemestreRepository, ProMateriaSemestreServiceImpl> {
    public ProMateriaSemestreResource(ProMateriaSemestreServiceImpl service){
        super(service);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatos (@PathVariable("id") Integer codigo, @RequestBody ProMateriaSemestre obj) throws DataException{
        return (ResponseEntity<ProMateriaSemestre>) service.findById(codigo).map(datosGuardados-> {
            datosGuardados.setCodPeriodoSemestre((int) obj.getCodPeriodoSemestre().doubleValue());
            datosGuardados.setCodMateria((int) obj.getCodMateria().doubleValue());
            datosGuardados.setEstado(obj.getEstado());
            datosGuardados.setCodAula((int) obj.getCodAula().doubleValue());
            datosGuardados.setNumeroHoras(obj.getNumeroHoras());
            datosGuardados.setNotaMinima(obj.getNotaMinima());
            datosGuardados.setNotaMaxima(obj.getNotaMaxima());
            return super.actualizarDatos(datosGuardados);
        }).orElseGet(()->ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/listar")
    public List<ProMateriaSemestreDto> findByCodigoSemestre(@PathVariable("id") Integer codigo){
        return service.findByCodigo(codigo);
    }

}
