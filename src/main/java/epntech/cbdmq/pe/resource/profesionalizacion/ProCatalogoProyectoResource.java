package epntech.cbdmq.pe.resource.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProCatalogoProyecto;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.profesionalizacion.ProCatalogoProyectoRepository;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ProCatalogoProyectoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proCatalogoProyecto")
public class ProCatalogoProyectoResource extends ProfesionalizacionResource<ProCatalogoProyecto, Integer, ProCatalogoProyectoRepository, ProCatalogoProyectoServiceImpl> {
    public ProCatalogoProyectoResource(ProCatalogoProyectoServiceImpl service) {
        super(service);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody ProCatalogoProyecto obj) throws DataException {
        return (ResponseEntity<ProCatalogoProyecto>) service.findById(codigo).map(datosGuardados -> {
            datosGuardados.setCodigoTipoProyecto(obj.getCodigoTipoProyecto());
            datosGuardados.setNombreCatalogo(obj.getNombreCatalogo());
            datosGuardados.setEstado(obj.getEstado());
            return super.actualizarDatos(datosGuardados);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
