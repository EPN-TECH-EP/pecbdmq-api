package epntech.cbdmq.pe.resource.especializacion;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.util.List;

import epntech.cbdmq.pe.dominio.admin.Estados;
import epntech.cbdmq.pe.dominio.util.ModuloEstadosData;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.especializacion.CursoEstado;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.especializacion.CursoEstadoServiceImpl;

@RestController
@RequestMapping("/cursoEstado")
public class CursoEstadoResource {

    @Autowired
    private CursoEstadoServiceImpl cursoEstadoServiceImpl;

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> guardar(@RequestBody CursoEstado obj) throws DataException {
        return new ResponseEntity<>(cursoEstadoServiceImpl.save(obj), HttpStatus.OK);
    }

    @GetMapping("/listar")
    public List<CursoEstado> listar() {
        return cursoEstadoServiceImpl.listarTodo();
    }

    @GetMapping("/listar/{codTipoCurso}")
    public List<CursoEstado> listarByTipoCurso(@PathVariable("codTipoCurso") Long codTipoCurso) throws DataException {
        return cursoEstadoServiceImpl.listarByTipoCurso(codTipoCurso);
    }
    @GetMapping("/listarModuloEstados/{codTipoCurso}")
    public List<ModuloEstadosData> listarModuloEstadosByTipoCurso(@PathVariable("codTipoCurso") Long codTipoCurso) throws DataException {
        return cursoEstadoServiceImpl.listarModuloEstadosByTipoCurso(codTipoCurso);
    }
    @GetMapping("/listarEstados/{codTipoCurso}")
    public List<Estados> listarEstadosByTipoCurso(@PathVariable("codTipoCurso") Long codTipoCurso) throws DataException {
        return cursoEstadoServiceImpl.listarEstadosByTipoCurso(codTipoCurso);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CursoEstado> obtenerPorId(@PathVariable("id") long codigo) throws DataException {
        return cursoEstadoServiceImpl.getById(codigo).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/activo/{id}")
    public ResponseEntity<HttpResponse> getEstado(@PathVariable("id") long codigo) {
        String result = cursoEstadoServiceImpl.getEstadoByCurso(codigo);

        if (result == null) {
            result = "SIN ESTADO";
        }

        return response(HttpStatus.OK, result);
    }
    @GetMapping("/actualizarEstado/{idCurso}/{idCursoEstado}")
    public ResponseEntity<HttpResponse> nextState(
                                                  @PathVariable("idCurso") Integer idCurso,
                                                  @PathVariable("idCursoEstado") Integer idCursoEstado) {
        String result = cursoEstadoServiceImpl.updateState(idCurso,idCursoEstado);

        return response(HttpStatus.OK, result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoEstado> actualizarDatos(@PathVariable("id") long codigo, @RequestBody CursoEstado obj) throws DataException {
        return (ResponseEntity<CursoEstado>) cursoEstadoServiceImpl.getById(codigo).map(datosGuardados -> {
            datosGuardados.setCodCatalogoEstados(obj.getCodCatalogoEstados());
            datosGuardados.setCodTipoCurso(obj.getCodTipoCurso());
            datosGuardados.setOrden(obj.getOrden());
            datosGuardados.setEstado(obj.getEstado());

            CursoEstado datosActualizados = null;
            try {
                datosActualizados = cursoEstadoServiceImpl.update(datosGuardados);
            } catch (DataException e) {
                // TODO Auto-generated catch block
                return response(HttpStatus.BAD_REQUEST, e.getMessage());
            }
            return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") long codigo) throws DataException {

        cursoEstadoServiceImpl.delete(codigo);
        return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
