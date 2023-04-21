package epntech.cbdmq.pe.servicio;

import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.PostulanteDatos;

public interface PostulanteDatosService {
	
	Optional<PostulanteDatos> getDatos(Integer codPostulante);
}
