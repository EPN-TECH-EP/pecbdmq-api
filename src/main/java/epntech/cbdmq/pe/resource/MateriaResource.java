package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Materia;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.MateriaServiceImpl;

@RestController
@RequestMapping("/materia")
@Tag(name = "MATERIA", description = "API que devuelve las diferentes opeaciones en lo que respecta a las materias")
public class MateriaResource {

    @Autowired
    private MateriaServiceImpl objService;

    @Operation(summary = "Crea una materia a partir de un objeto materia")
    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> guardar(@RequestBody Materia obj) throws DataException {
        obj.setNombre(obj.getNombre().toUpperCase());
        return new ResponseEntity<>(objService.save(obj), HttpStatus.OK);
    }
    @Operation(summary = "Trae la lista de materias")
    @GetMapping("/listar")
    public List<Materia> listar() {
        return objService.getAll();
    }

    @Operation(summary = "Trae una materia por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Materia.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "La materia no fue encontrada", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public ResponseEntity<Materia> obtenerPorId(@PathVariable("id") int codigo) {
        return objService.getById(codigo).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Materia> actualizarDatos(@Parameter(description = "Objeto de tipo materia, en donde se encuentran las propiedades ya actualizadas") @PathVariable("id") int codigo,
                                                   @RequestBody Materia obj) throws DataException {
        return (ResponseEntity<Materia>) objService.getById(codigo).map(datosGuardados -> {
            datosGuardados.setNombre(obj.getNombre().toUpperCase());
            //datosGuardados.setNumHoras(obj.getNumHoras());
            datosGuardados.setTipoMateria(obj.getTipoMateria());
            //datosGuardados.setObservacionMateria(obj.getObservacionMateria());
            //datosGuardados.setPesoMateria(obj.getPesoMateria());
            //datosGuardados.setNotaMinima(obj.getNotaMinima());
            datosGuardados.setEstado(obj.getEstado());

            Materia datosActualizados = null;

            try {
                datosActualizados = objService.update(datosGuardados);
            } catch (DataException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
                return response(HttpStatus.BAD_REQUEST, e.getMessage().toString());
            }
            return new ResponseEntity<>(datosActualizados, HttpStatus.OK);


        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponse> eliminarDatos(@Parameter(description = "Buscar materia con ese id", required = true) @PathVariable("id") int codigo) throws DataException {
        objService.delete(codigo);
        return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
