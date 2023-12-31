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
import epntech.cbdmq.pe.dominio.admin.EvaluacionDocente;
import epntech.cbdmq.pe.dominio.admin.TipoPrueba;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.EvaluacionDocenteServiceImpl;


@RestController
@RequestMapping("/evaluaciondocente")
public class EvaluacionDocenteResource {

	@Autowired
    private EvaluacionDocenteServiceImpl objServices;
	
	 @PostMapping("/crear")
	    @ResponseStatus(HttpStatus.CREATED)
	    public ResponseEntity<?> guardar(@RequestBody EvaluacionDocente obj) throws DataException {
	    	return new ResponseEntity<>(objServices.save(obj), HttpStatus.OK);
	    }
	 
	 @GetMapping("/listar")
	    public List<EvaluacionDocente> listar() {
	        return objServices.getAll();
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<EvaluacionDocente> obtenerDatosPorId(@PathVariable("id") Integer codigo) {
	        return objServices.getById(codigo).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }
	
	
	    @PutMapping("/{id}")
	    public ResponseEntity<EvaluacionDocente> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody EvaluacionDocente obj) {
	        return objServices.getById(codigo).map(datosGuardados -> {
	            //datosGuardados.setCod_evaluacion_docente(obj.getCod_evaluacion_docente());
	            datosGuardados.setNombre(obj.getNombre());
	            datosGuardados.setEstado(obj.getEstado());
	            EvaluacionDocente datosActualizados = objServices.update(datosGuardados);
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
