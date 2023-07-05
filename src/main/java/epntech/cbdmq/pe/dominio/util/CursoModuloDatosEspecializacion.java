package epntech.cbdmq.pe.dominio.util;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoModuloDatosEspecializacion {

	private Long codCursoModelo;
	private Long codEspModulo;
	private String nombreEspModulo;
	private Long codCursoEspecializacion;
	private String nombreCatalogoCurso;
	private BigDecimal porcentaje;
	
}
