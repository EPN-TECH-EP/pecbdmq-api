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
import epntech.cbdmq.pe.dominio.admin.Baja;
import epntech.cbdmq.pe.dominio.admin.TipoSancion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.BajaServiceImpl;

@RestController
@RequestMapping("/baja")
public class BajaResource {
	 @Autowired
	    private BajaServiceImpl objServices;

	 @PostMapping("/crear")
	    @ResponseStatus(HttpStatus.CREATED)
	    public ResponseEntity<?> guardar(@RequestBody Baja obj) throws DataException {
	    	return new ResponseEntity<>(objServices.save(obj), HttpStatus.OK);
	    }
	
	 @GetMapping("/listar")
	    public List<Baja> listar() {
	        return objServices.getAll();
	    }
	 
	 @GetMapping("/{id}")
	    public ResponseEntity<Baja> obtenerDatosPorId(@PathVariable("id") Integer codigo) {
	        return objServices.getById(codigo).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	    }
	 
	 @PutMapping("/{id}")
	    public ResponseEntity<Baja> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody Baja obj) {
	        return (ResponseEntity<Baja>) objServices.getById(codigo).map(datosGuardados -> {
	            
	            datosGuardados.setCod_modulo(obj.getCod_modulo());
	            datosGuardados.setCod_baja(obj.getCod_baja());
	            datosGuardados.setFechacreabaja(obj.getFechabaja());
	            datosGuardados.setDescripcionbaja(obj.getDescripcionbaja());
	            datosGuardados.setNombre(obj.getNombre());
	            datosGuardados.setFechacreabaja(obj.getFechacreabaja());
	            datosGuardados.setHoracreabaja(obj.getHoracreabaja());
	            datosGuardados.setUsuariomodbaja(obj.getUsuariomodbaja());
	            datosGuardados.setFechamodbaja(obj.getFechamodbaja());
	            datosGuardados.setHoramodbaja(obj.getHoramodbaja());
	           // datosGuardados.setRutaadjuntobaja(obj.getRutaadjuntobaja());
	            datosGuardados.setCod_baja(obj.getCod_baja());           
	            datosGuardados.setEstado(obj.getEstado());
	            Baja datosActualizados=null;
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
		public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") Integer codigo) {
			objServices.delete(codigo);
			return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
		}
	    
	    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
	        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
	                message), httpStatus);
	    }
	
}
