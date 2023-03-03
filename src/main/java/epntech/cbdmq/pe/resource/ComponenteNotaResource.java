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
import epntech.cbdmq.pe.excepcion.dominio.DataException;

import epntech.cbdmq.pe.dominio.admin.ComponenteNota;

import epntech.cbdmq.pe.servicio.impl.ComponenteNotaServiceImpl;


/**
 * @author EPN TECH
 * @version $Revision: $
 */
@RestController
@RequestMapping("/gen_componente_nota")
public class ComponenteNotaResource {
    @Autowired
    private ComponenteNotaServiceImpl objServices;

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> guardar(@RequestBody ComponenteNota obj) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(objServices.save(obj));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
        }
    }

    @GetMapping("/listar")
    public List<ComponenteNota> listar() {
        return objServices.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComponenteNota> obtenerDatosPorId(@PathVariable("id") Integer codigo) {
        return objServices.getById(codigo).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComponenteNota> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody ComponenteNota obj) {
        return objServices.getById(codigo).map(datosGuardados -> {
            datosGuardados.setCod_componente_nota(obj.getCod_componente_nota());
            datosGuardados.setComponentenota(obj.getComponentenota());
            datosGuardados.setEstado(obj.getEstado());
            ComponenteNota datosActualizados = objServices.update(datosGuardados);
            return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") Integer codigo) throws DataException {
		objServices.delete(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message){
    	return new ResponseEntity<>(new HttpResponse(
    			httpStatus.value(), 
    			httpStatus, 
    			httpStatus.
    			getReasonPhrase().
    			toUpperCase(),
                message), 
    			httpStatus);

    }

   
}
