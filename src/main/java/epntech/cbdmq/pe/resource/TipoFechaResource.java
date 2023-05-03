/*
 * Copyright 2008-2023. Todos los derechos reservados.
 */
package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.util.List;

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
import epntech.cbdmq.pe.dominio.admin.TipoFecha;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.TipoFechaServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author EPN TECH
 * @version $Revision: $
 */
@RestController
@RequestMapping("/gen_tipo_fecha")
public class TipoFechaResource {
    @Autowired
    private TipoFechaServiceImpl objServices;

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> guardar(@RequestBody TipoFecha obj) throws DataException {
    	obj.setFecha(obj.getFecha().toUpperCase());
    	return new ResponseEntity<>(objServices.save(obj), HttpStatus.OK);
    }

    @GetMapping("/listar")
    public List<TipoFecha> listar() {
        return objServices.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoFecha> obtenerDatosPorId(@PathVariable("id") String fecha) {
        return objServices.getById(fecha).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatos(@PathVariable("id") String fecha, @RequestBody TipoFecha obj) throws DataException {
        return (ResponseEntity<TipoFecha>) objServices.getById(fecha).map(datosGuardados -> {
            datosGuardados.setFecha(obj.getFecha().toUpperCase());
            datosGuardados.setEstado(obj.getEstado());
            TipoFecha datosActualizados = null;
			try {
				datosActualizados = objServices.update(datosGuardados);
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return response(HttpStatus.BAD_REQUEST, e.getMessage().toString());
			}
            return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
   	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") String codigo) {
       	objServices.delete(codigo);
   		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
   	}
       
       private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
           return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                   message), httpStatus);
       }
}
