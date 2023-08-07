package epntech.cbdmq.pe.dominio.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionDatosEspecializacion {

	private Long codInscripcion;
	private String cedula;
	private String nombre;
	private String apellido;
	private String nombreCatalogoCurso;
	private String estado;
	private String nombreUsuario;
	private String correoUsuario;
	private Long codUsuario;
	
}
