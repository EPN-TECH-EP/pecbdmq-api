package epntech.cbdmq.pe.dominio.admin.especializacion;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ConvocatoriaCursoData {

	@Id
	private Long codConvocatoria;
	private LocalDate fechaInicioConvocatoria;
	private LocalDate fechaFinConvocatoria;
	private LocalTime horaInicioConvocatoria;
	private LocalTime horaFinConvocatoria;
	private String emailNotificacion;
	private String nombreCatalogoCurso;

}
