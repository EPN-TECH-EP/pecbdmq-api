/*
 * Copyright 2008-2023. Todos los derechos reservados.
 */
package epntech.cbdmq.pe.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.admin.TipoFecha;
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
    public ResponseEntity<?> guardar(@RequestBody TipoFecha obj) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(objServices.save(obj));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
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
    public ResponseEntity<TipoFecha> actualizarDatos(@PathVariable("id") String fecha, @RequestBody TipoFecha obj) {
        return objServices.getById(fecha).map(datosGuardados -> {
            datosGuardados.setFecha(obj.getFecha());
            TipoFecha datosActualizados = objServices.update(datosGuardados);
            return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/eliminar/{}id")
    public ResponseEntity<TipoFecha> eliminarDatos(@PathVariable("id") String fecha, @RequestBody TipoFecha obj) {
        return objServices.getById(fecha).map(datosGuardados -> {
            datosGuardados.setFecha(obj.getFecha());
            TipoFecha datosActualizados = objServices.update(datosGuardados);
            return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping("/api")
    public String home(HttpServletRequest request) throws Exception {
        return String.format("Servicio (%s)", request.getRequestURL());
    }
}
