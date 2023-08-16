package epntech.cbdmq.pe.resource.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProConvocatoria;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.profesionalizacion.ProConvocatoriaRepository;
import epntech.cbdmq.pe.servicio.EmailService;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ProConvocatoriaServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/proConvocatoria")
public class ProConvocatoriaResource extends ProfesionalizacionResource<ProConvocatoria, Integer, ProConvocatoriaRepository, ProConvocatoriaServiceImpl> {
    private final EmailService emailService;

    public ProConvocatoriaResource(ProConvocatoriaServiceImpl service, EmailService emailService) {
        super(service);
        this.emailService = emailService;
    }

    @GetMapping("/listarActiva")
    public List<ProConvocatoria> listarActiva() {
        List<ProConvocatoria> listar = super.listar();
        List<ProConvocatoria> listarResponse;
        listarResponse = listar.stream().filter(x -> x.getFechaInicio().getTime() <= Calendar.getInstance().getTime().getTime() &&
                x.getFechaFin().getTime() >= Calendar.getInstance().getTime().getTime()).toList();
        return listarResponse;
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> guardar(@RequestBody ProConvocatoria obj) throws DataException {
        ProConvocatoria saveItem = service.save(obj);
        this.emailService.sendConvocatoriaProfesionalizacionEmail(obj.getCorreo());
        return new ResponseEntity<>(saveItem, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody ProConvocatoria obj) throws DataException {

        return service.findById(codigo).map(datosGuardados -> {
            datosGuardados.setCodigoSemestre(obj.getCodigoSemestre());
            datosGuardados.setCodigoParametro((int) obj.getCodigoParametro().doubleValue());
            datosGuardados.setCodigoParametro2((int) obj.getCodigoParametro2().doubleValue());
            datosGuardados.setEstado(obj.getEstado());
            datosGuardados.setCodigoUnicoConvocatoria(obj.getCodigoUnicoConvocatoria());
            datosGuardados.setNombre(obj.getNombre());
            datosGuardados.setFechaInicio(obj.getFechaInicio());
            datosGuardados.setFechaFin(obj.getFechaFin());
            datosGuardados.setFechaActual(obj.getFechaActual());
            datosGuardados.setCodPeriodo(obj.getCodPeriodo());
            datosGuardados.setCodPeriodo(obj.getCodPeriodo());
            datosGuardados.setCorreo(obj.getCorreo());
            ResponseEntity<?> responseEntity = super.actualizarDatos(datosGuardados);
            this.emailService.sendConvocatoriaProfesionalizacionEmail(obj.getCorreo());
            return responseEntity;
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/codigoUnicoCreacion")
    public String getCodLastConvocatoria() {
        return service.getCodConvocatoriaCreacion();

    }

    @GetMapping("{id}/periodo")
    public Optional<ProConvocatoria> getByPeriodo(@PathVariable("id") Integer id) {
        return service.getByPeriodo(id);
    }

    @PostMapping("{id}/actualizaEstado")
    public ProConvocatoria updateEstadoConvocatoria(@PathVariable("id") Integer id, @RequestBody String estado) throws DataException {
        return service.updateEstadoConvocatoria(id, estado);
    }
}
