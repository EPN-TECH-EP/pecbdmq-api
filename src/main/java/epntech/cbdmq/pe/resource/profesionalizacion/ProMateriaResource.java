package epntech.cbdmq.pe.resource.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.EjeMateria;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProMateria;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProMateriaSemestreDto;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProMateriaRepository;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ProMateriaServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proMateria")
public class ProMateriaResource extends ProfesionalizacionResource<ProMateria, Integer, ProMateriaRepository, ProMateriaServiceImpl> {
    public ProMateriaResource(ProMateriaServiceImpl service) {
        super(service);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody ProMateria obj) throws DataException {
        return (ResponseEntity<ProMateria>) service.findById(codigo).map(datosGuardados -> {
            datosGuardados.setNombre(obj.getNombre());
            datosGuardados.setCodEjeMateria(obj.getCodEjeMateria());
            datosGuardados.setEstado(obj.getEstado());
            datosGuardados.setEsProyecto(obj.getEsProyecto());
            return super.actualizarDatos(datosGuardados);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("ejeMaterias")
    public List<EjeMateria> getEjeMateria() {
        return service.getEjeMateria();
    }

    @GetMapping("/filtrar/{semestre}/{periodo}")
    public List<ProMateriaSemestreDto> getByAll(@PathVariable("semestre") Integer codSemestre, @PathVariable("periodo") Integer codPeriodo) {
        if (codSemestre > 0 && codPeriodo > 0) {
            return service.getByAll(codSemestre, codPeriodo);
        } else if (codSemestre > 0) {
            return service.getByAll(codSemestre);
        } else if (codPeriodo > 0) {
            return service.getByAllPeriodo(codPeriodo);
        }
        return service.getByAll();
    }
}
