package epntech.cbdmq.pe.resource.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProInscripcionesDelegados;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProInscripcionesDelegadosDto;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProInscripcionesDelegadosRepository;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ProInscripcionesDelegadosServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proInscripcionesDelegados")
public class ProInscripcionesDelegadosResource extends ProfesionalizacionResource<ProInscripcionesDelegados, Integer, ProInscripcionesDelegadosRepository, ProInscripcionesDelegadosServiceImpl> {
    public ProInscripcionesDelegadosResource(ProInscripcionesDelegadosServiceImpl service) {
        super(service);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody ProInscripcionesDelegados obj) throws DataException {
        return (ResponseEntity<ProInscripcionesDelegados>) service.findById(codigo).map(datosGuardados -> {
            datosGuardados.setCodInscripciones(obj.getCodInscripciones());
            datosGuardados.setCodDelegados(obj.getCodDelegados());
            datosGuardados.setEstado(obj.getEstado());
            return super.actualizarDatos(datosGuardados);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/datos/{id}/listar")
    public List<ProInscripcionesDelegadosDto> findByCodConvocatoria(@PathVariable("id") Integer cod_convocatoria){
        return service.findByCodConvocatoria(cod_convocatoria);
    }

    @GetMapping("/datos/{id}/listar/{id2}")
    List<ProInscripcionesDelegadosDto> findByCodConvocatoriaAnAndCodDelegados(@PathVariable("id") Integer cod_convocatoria, @PathVariable("id2") Integer cod_delegado){
        return service.findByCodConvocatoriaAnAndCodDelegados(cod_convocatoria,cod_delegado);
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> guardar(@RequestBody ProInscripcionesDelegados obj) throws DataException {
        return new ResponseEntity<>(service.save(obj), HttpStatus.OK);
    }

}
