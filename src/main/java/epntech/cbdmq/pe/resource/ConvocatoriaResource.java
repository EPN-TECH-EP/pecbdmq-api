package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.util.List;
import java.util.Set;

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
import epntech.cbdmq.pe.dominio.admin.Convocatoria;
import epntech.cbdmq.pe.dominio.admin.Convocatorialistar;




import epntech.cbdmq.pe.dominio.admin.PonderacionModulos;

import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.ConvocatoriaServicieImpl;

@RestController
@RequestMapping("/convocatoria")
public class ConvocatoriaResource {

	@Autowired
	private ConvocatoriaServicieImpl objService;
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> guardar(@RequestBody Convocatoria obj) throws DataException{
		return new ResponseEntity<>(objService.saveData(obj), HttpStatus.OK);
	}
	
	@GetMapping("/listar")
	public List<Convocatoria> listar() {
		return objService.getAllData();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Convocatoria> obtenerPorId(@PathVariable("id") int codigo) {
		return objService.getByIdData(codigo).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@GetMapping("/activa")
	public Set<Convocatoria> obtenerConvocatoriaActiva() throws DataException {
		return objService.getConvocatoriaActiva();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Convocatoria> actualizarDatos(@PathVariable("id") int codigo, @RequestBody Convocatoria obj) {
		return (ResponseEntity<Convocatoria>) objService.getByIdData(codigo).map(datosGuardados -> {
			datosGuardados.setNombre(obj.getNombre());
			datosGuardados.setEstado(obj.getEstado());
			datosGuardados.setFechaInicioConvocatoria(obj.getFechaInicioConvocatoria());
			datosGuardados.setFechaFinConvocatoria(obj.getFechaFinConvocatoria());
			datosGuardados.setFechaActual(obj.getFechaActual());
			datosGuardados.setHoraInicioConvocatoria(obj.getHoraInicioConvocatoria());
			datosGuardados.setHoraFinConvocatoria(obj.getHoraFinConvocatoria());
			

			datosGuardados.setCupoHombres(obj.getCupoHombres());
			datosGuardados.setCupoMujeres(obj.getCupoMujeres());
			datosGuardados.setCorreo(obj.getCorreo());


			Convocatoria datosActualizados=null;
			try {
				datosActualizados = objService.updateData(datosGuardados);
			} catch (DataException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				return response(HttpStatus.BAD_REQUEST, e.getMessage().toString());
			}
			return new ResponseEntity<>(datosActualizados, HttpStatus.OK);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") int codigo) throws DataException {
		objService.deleteData(codigo);
		return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
	
	@GetMapping("/listarconvocatorias")

	public List<Convocatorialistar> listarTodo() {
		return objService.getConvocatorialistar();

	}
	@GetMapping("/codigoUnicoCreacion")

	public String getCodLastConvocatoria()  {
		return objService.getCodConvocatoriaCreacion();

	}
	
}
