package epntech.cbdmq.pe.dominio.util;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionEstudianteDatosEspecializacion {
	
	private Long codInscripcion;
	private String cedula;
	private String nombre;
	private String apellido;
	private String correoPersonal;
	private String nombreCatalogoCurso;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaInicioCurso;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaFinCurso;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaInscripcion;
	
}
