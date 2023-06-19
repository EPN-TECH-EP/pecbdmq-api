package epntech.cbdmq.pe.dominio.admin.especializacion;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "esp_curso_documento")
@Data
public class CursoDocumento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_curso_documento")
	private Long codCursoDocumento;
	
	@Column(name = "cod_curso_especializacion")
	private Long codCursoEspecializacion;
	
	@Column(name = "cod_documento")
	private Long codDocumento;
	
	@Column(name = "aprobado")
	private Boolean aprobado;
	
	@Column(name = "observaciones")
	private String observaciones;
	
	@Column(name = "validado")
	private Boolean validado;

}
