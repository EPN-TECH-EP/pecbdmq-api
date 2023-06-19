package epntech.cbdmq.pe.dominio.admin.especializacion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "esp_curso_requisito")
public class CursoRequisito {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_curso_requisito")
	private Long codCursoRequisito;
	
	@Column(name = "cod_curso_especializacion")
	private Long codCursoEspecializacion;
	
	@Column(name = "cod_requisito")
	private Long codRequisito;

}
