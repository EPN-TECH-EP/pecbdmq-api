package epntech.cbdmq.pe.resource.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProCumpleRequisitos;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProInscripcionDto;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.repository.ProInscripcionDatosRepository;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProCumpleRequisitosRepository;
import epntech.cbdmq.pe.servicio.EmailService;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ProCumpleRequisitosServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/proCumpleRequisistos")
public class ProCumpleRequisitosResource extends ProfesionalizacionResource<ProCumpleRequisitos, Integer, ProCumpleRequisitosRepository, ProCumpleRequisitosServiceImpl> {
    private final EmailService emailService;
    private final ProInscripcionDatosRepository datosRepository;

    public ProCumpleRequisitosResource(ProCumpleRequisitosServiceImpl service, EmailService emailService, ProInscripcionDatosRepository datosRepository) {
        super(service);
        this.emailService = emailService;
        this.datosRepository = datosRepository;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody ProCumpleRequisitos obj) throws DataException {
        return (ResponseEntity<ProCumpleRequisitos>) service.findById(codigo).map(datosGuardados -> {
            datosGuardados.setCodCumpleRequisito(obj.getCodCumpleRequisito());
            datosGuardados.setCodInscripcion(obj.getCodInscripcion());
            datosGuardados.setCodRequisito(obj.getCodRequisito());
            datosGuardados.setEstado(obj.getEstado());
            datosGuardados.setObservaciones(obj.getObservaciones());
            datosGuardados.setObservacionMuestra(obj.getObservacionMuestra());
            return super.actualizarDatos(datosGuardados);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/actualizaRequisitos")
    public boolean actualizaCumpleRequisitos(@RequestBody List<ProCumpleRequisitos> obj) {
        boolean aprobado = true;
        for (ProCumpleRequisitos proCumpleRequisitos : obj) {
            if (!proCumpleRequisitos.getCumple()) {
                aprobado = false;
            }
            if (proCumpleRequisitos.getCodCumpleRequisito() != null && proCumpleRequisitos.getCodCumpleRequisito() > 0) {
                super.actualizarDatos(proCumpleRequisitos);
            } else {
                try {
                    super.guardar(proCumpleRequisitos);
                } catch (DataException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (aprobado && obj.size() > 0) {
            Optional<ProInscripcionDto> proDatosInscripcion = datosRepository.getProDatosInscripcion(obj.get(0).getCodInscripcion());
            if (proDatosInscripcion.isPresent()){
                ProInscripcionDto proInscripcionDto = proDatosInscripcion.get();
                this.emailService.sendAprobadoValidacionProEmail(proInscripcionDto.getCorreoPersonal());

            }
            return service.apruebaInscripcion(obj.get(0).getCodInscripcion());
        } else if (!aprobado && obj.size() > 0) {
            Optional<ProInscripcionDto> proDatosInscripcion = datosRepository.getProDatosInscripcion(obj.get(0).getCodInscripcion());
            if (proDatosInscripcion.isPresent()){
                ProInscripcionDto proInscripcionDto = proDatosInscripcion.get();
                this.emailService.sendRechazadoValidacionProEmail(proInscripcionDto.getCorreoPersonal());
            }
            return service.rechazaInscripcion(obj.get(0).getCodInscripcion());
        }
        return true;
    }

}
