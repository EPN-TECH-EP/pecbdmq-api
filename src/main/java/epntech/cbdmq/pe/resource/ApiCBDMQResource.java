package epntech.cbdmq.pe.resource;

import java.util.Optional;

import epntech.cbdmq.pe.servicio.ApiCBDMQFuncionariosService;
import epntech.cbdmq.pe.servicio.ApiCBDMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.util.ApiBase;
import epntech.cbdmq.pe.dominio.util.ApiEducacionMedia;
import epntech.cbdmq.pe.dominio.util.ApiEducacionSuperior;
import epntech.cbdmq.pe.servicio.impl.APICBDMQServiceImpl;

@RestController
@RequestMapping("/apicbdmq")
public class ApiCBDMQResource {
	
	@Autowired
	private ApiCBDMQService objService;
	@Autowired
	private ApiCBDMQFuncionariosService objFuncionariosService;

	@GetMapping("/ciudadanos/{cedula}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> getData(@PathVariable("cedula") String cedula) throws Exception{
		
		try {
			return new ResponseEntity<>(objService.servicioCiudadanos(cedula), HttpStatus.OK);
		}catch(Exception ex) {

			return response(HttpStatus.BAD_REQUEST, ex.getMessage());
			
			/*Optional<?> data = Optional.empty();
			
			ApiBase base = new ApiBase();
			base.setData(data);
			base.setMessage(ex.getMessage());
			base.setStatus("error");
			
			Optional<ApiBase> b = Optional.of(base);
			
			return b;*/
		}
	}
		@GetMapping("/funcionarios/{cedula}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> getDataFuncionarios(@PathVariable("cedula") String cedula) throws Exception{

		try {
			return new ResponseEntity<>(objFuncionariosService.servicioFuncionarios(cedula), HttpStatus.OK);
		}catch(Exception ex) {

			return response(HttpStatus.BAD_REQUEST, ex.getMessage());

			/*Optional<?> data = Optional.empty();

			ApiBase base = new ApiBase();
			base.setData(data);
			base.setMessage(ex.getMessage());
			base.setStatus("error");

			Optional<ApiBase> b = Optional.of(base);

			return b;*/
		}
	}

	@GetMapping("/educacionMedia/{cedula}")
	public ResponseEntity<?> getEducacionMedia(@PathVariable("cedula") String cedula) throws Exception{
		try {
			//return objService.servicioEducacionMedia(cedula);
			return new ResponseEntity<>(objService.servicioEducacionMedia(cedula), HttpStatus.OK);
		}catch(Exception ex) {
			//throw new Exception(ex.getMessage());
			return response(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}
	
	@GetMapping("/educacionSuperior/{cedula}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> getEducacionSuperior(@PathVariable("cedula") String cedula) throws Exception{
		try{
			//return objService.servicioEducacionSuperior(cedula);
			return new ResponseEntity<>(objService.servicioEducacionSuperior(cedula), HttpStatus.OK);
		}catch(Exception ex) {
			//throw new Exception(ex.getMessage());
			return response(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
	}
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
