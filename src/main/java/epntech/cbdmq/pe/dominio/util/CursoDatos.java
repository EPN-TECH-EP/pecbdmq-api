package epntech.cbdmq.pe.dominio.util;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
public class CursoDatos {

	@Id
	private Long codCursoEspecializacion;
	private Long codAula;
	private Integer numeroCupo;
	private LocalDate fechaInicioCurso;
	private LocalDate fechaFinCurso;
	private BigDecimal notaMinima;
	private Boolean tieneModulos;
	private Long codTipoCurso;
	private String nombreCatalogoCurso;
}
