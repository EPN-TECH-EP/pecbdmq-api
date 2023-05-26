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
import epntech.cbdmq.pe.dominio.admin.EncuestaResumen;
import epntech.cbdmq.pe.dominio.admin.TipoNota;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.EncuestaResumenServiceImpl;

@RestController
@RequestMapping("/encuestaresumen")
public class EncuestaResumenResource {

	
	@Autowired
	private EncuestaResumenServiceImpl objServices;
	
	
	@PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> guardar(@RequestBody EncuestaResumen obj) throws DataException {
    	obj.setDescripcion(obj.getDescripcion().toUpperCase());
    	return new ResponseEntity<>(objServices.save(obj), HttpStatus.OK);
    }
	
	@GetMapping("/listar")
    public List<EncuestaResumen> listar() {
        return objServices.getAll();
    }
	
	 @GetMapping("/{id}")
	    public ResponseEntity<EncuestaResumen> obtenerDatosPorId(@PathVariable("id") Integer codigo) {
	        return objServices.getById(codigo).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }
	 
	 @PutMapping("/{id}")
	    public ResponseEntity<?> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody EncuestaResumen obj) throws DataException {
	        return (ResponseEntity<EncuestaResumen>) objServices.getById(codigo).map(datosGuardados -> {
	        	datosGuardados.setCod_modulo(obj.getCod_modulo());
	            datosGuardados.setDescripcion(obj.getDescripcion().toUpperCase());
	            datosGuardados.setFecha_inicio(obj.getFecha_inicio());
	            datosGuardados.setFecha_fin(obj.getFecha_fin());
	            datosGuardados.setEstado(obj.getEstado());
	            
	            EncuestaResumen datosActualizados = null;
				try {
					datosActualizados = objServices.update(datosGuardados);
				} catch (DataException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					return response(HttpStatus.BAD_REQUEST, e.getMessage().toString());
				}
	            return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
	        }).orElseGet(() -> ResponseEntity.notFound().build());
	    }
	
	 @DeleteMapping("/{id}")
		public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") Integer codigo) {
	    	objServices.delete(codigo);
			return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
		}
	    
	    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
	        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
	                message), httpStatus);
	    }

}
