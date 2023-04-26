package epntech.cbdmq.pe.servicio;

import java.util.Optional;

import epntech.cbdmq.pe.dominio.util.PostulanteDatos;

public interface PostulanteDatosService {
	
	Optional<PostulanteDatos> getDatos(Integer codPostulante);
}
