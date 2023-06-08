package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "esp_tipo_curso")
public class EspTipoCurso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_tipo_curso")
	private Long cod_tipo_curso;
	
	@Column(name = "nombre_tipo_curso")
	private String Nombre;
	
	@Column(name = "estado")
	private String estado;
	
}
