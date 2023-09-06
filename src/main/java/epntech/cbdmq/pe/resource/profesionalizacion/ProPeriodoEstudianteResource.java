package epntech.cbdmq.pe.resource.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.UsuarioDatoPersonal;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProPeriodoEstudiante;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProPeriodoSemestre;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProPeriodoEstudianteDto;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProPeriodoEstudianteRepository;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ProPeriodoEstudianteServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proPeriodoEstudiante")
public class ProPeriodoEstudianteResource extends ProfesionalizacionResource<ProPeriodoEstudiante, Integer, ProPeriodoEstudianteRepository, ProPeriodoEstudianteServiceImpl> {
    public ProPeriodoEstudianteResource(ProPeriodoEstudianteServiceImpl service) {
        super(service);
    }

    @GetMapping("/{id}/listar")
    public List<ProPeriodoEstudianteDto> getAllByPeriodo(@PathVariable("id") Integer codigo) {
        return service.getAllByPeriodo(codigo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody ProPeriodoEstudiante obj) throws DataException {
        return (ResponseEntity<ProPeriodoSemestre>) service.findById(codigo).map(datosGuardados -> {
            datosGuardados.setCodDatosPersonales(obj.getCodDatosPersonales());
            datosGuardados.setCodPeriodo(obj.getCodPeriodo());
            datosGuardados.setEstado(obj.getEstado());
            return super.actualizarDatos(datosGuardados);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar/identificacion/{identificacion}")
    public UsuarioDatoPersonal getByIdentificacion(@PathVariable("identificacion") String identificacion) {
        return service.findByCedula(identificacion);
    }

    @GetMapping("/buscar/correo/{correo}")
    public List<UsuarioDatoPersonal> getByCorreo(@PathVariable("correo") String correo) {
        return service.findByCorreo(correo);
    }

    @GetMapping("/buscar/datos")
    public List<UsuarioDatoPersonal> getByIdentificacion(@RequestParam String nombre, @RequestParam String apellido) {
        return service.findByNombreApellido(nombre, apellido);
    }
}
