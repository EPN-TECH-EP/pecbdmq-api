package epntech.cbdmq.pe.dominio.util;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity
public class EstacionTrabajoDto {

	@Id
	private Integer codigo;

	private String nombre;

	private String nombreCanton;

	private String nombreProvincia;

	private Integer canton;

	private Integer provincia;

	private String estado;
		
}
