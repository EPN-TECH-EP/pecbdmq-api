package epntech.cbdmq.pe.dominio.util;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class AntiguedadesFormacion {

	@Id
	private String codigoUnicoEstudiante;
	private String cedula;
	private String nombre;
	private String apellido;
	private String correoPersonal;
	private BigDecimal notaFinal; 
}

