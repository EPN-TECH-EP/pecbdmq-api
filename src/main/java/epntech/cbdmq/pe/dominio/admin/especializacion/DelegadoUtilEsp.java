package epntech.cbdmq.pe.dominio.admin.especializacion;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
public class DelegadoUtilEsp {
	@Id
	private Integer codEspDelegado;
	private Integer codUsuario;
	private Integer codDatosPersonales;
	private String estado; 
	private String cedula;
	private String nombre; 
	private String apellido;
	
}
