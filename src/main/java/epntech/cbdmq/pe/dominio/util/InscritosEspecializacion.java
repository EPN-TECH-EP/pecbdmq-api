package epntech.cbdmq.pe.dominio.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscritosEspecializacion {
	
	private Long codInscripcion;
	private String cedula;
	private String nombre;
	private String apellido;
	private String nombreCatalogoCurso;
	private String correoPersonal;
	private String codigoUnicoEstudiante;
	
}
