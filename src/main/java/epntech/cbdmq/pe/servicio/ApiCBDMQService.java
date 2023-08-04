package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.util.ApiEducacionMedia;
import epntech.cbdmq.pe.dominio.util.ApiEducacionSuperior;
import epntech.cbdmq.pe.dominio.util.CiudadanoApiDto;

public interface ApiCBDMQService {


	Optional<ApiEducacionMedia> servicioEducacionMedia(String cedula) throws Exception;
	
	Optional<ApiEducacionSuperior> servicioEducacionSuperior(String cedula) throws Exception;
	List<CiudadanoApiDto> servicioCiudadanos(String cedula) throws Exception;
}
