package epntech.cbdmq.pe.resource.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProDelegados;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProDelegadoDatosDto;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProDelegadosRepository;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ProDelegadosServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proDelegados")
public class ProDelegadosResource extends ProfesionalizacionResource<ProDelegados, Integer, ProDelegadosRepository, ProDelegadosServiceImpl> {

    public ProDelegadosResource(ProDelegadosServiceImpl service) {

        super(service);
    }

    @GetMapping("/listarAsignado")
    public List<ProDelegadoDatosDto> listarAsignados() {
        return service.getAllAssigned();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody ProDelegados obj) throws DataException {
        return (ResponseEntity<ProDelegados>) service.findById(codigo).map(datosGuardados -> {
            datosGuardados.setCodUsuario(obj.getCodUsuario());
            datosGuardados.setEstado(obj.getEstado());
            return actualizarDatos(datosGuardados);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
