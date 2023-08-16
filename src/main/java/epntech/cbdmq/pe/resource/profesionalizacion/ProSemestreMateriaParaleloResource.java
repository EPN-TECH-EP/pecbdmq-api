package epntech.cbdmq.pe.resource.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProSemestreMateriaParalelo;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProMateriaParaleloDto;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProEstudianteSemestreMateriaParaleloRepository;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ProSemestreMateriaParaleloImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proSemestreMateriaParalelo")
public class ProSemestreMateriaParaleloResource extends ProfesionalizacionResource<ProSemestreMateriaParalelo, Integer, ProEstudianteSemestreMateriaParaleloRepository, ProSemestreMateriaParaleloImpl> {
    public ProSemestreMateriaParaleloResource(ProSemestreMateriaParaleloImpl service){
        super(service);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody ProSemestreMateriaParalelo obj) throws DataException{
        return (ResponseEntity<ProSemestreMateriaParalelo>) service.findById(codigo).map(datosGuardados -> {
            datosGuardados.setCodSemestreMateria(obj.getCodSemestreMateria());
            datosGuardados.setCodParalelo(obj.getCodParalelo());
            datosGuardados.setEstado(obj.getEstado());
            datosGuardados.setCodProyecto(obj.getCodProyecto());
            return super.actualizarDatos(datosGuardados);
            }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/listar")
    public List<ProMateriaParaleloDto> getAllByCodSemestreMateria(@PathVariable("id") Integer codigo){
        return service.getAllByCodSemestreMateria(codigo);
    }


}
