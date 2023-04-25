package epntech.cbdmq.pe.servicio;

import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.ApiEducacionMedia;
import epntech.cbdmq.pe.dominio.admin.ApiEducacionSuperior;

public interface ApiCBDMQService {

	Optional<?> servicioCiudadanos(String cedula) throws Exception;
	
	Optional<ApiEducacionMedia> servicioEducacionMedia(String cedula) throws Exception;
	
	Optional<ApiEducacionSuperior> servicioEducacionSuperior(String cedula) throws Exception;
}
