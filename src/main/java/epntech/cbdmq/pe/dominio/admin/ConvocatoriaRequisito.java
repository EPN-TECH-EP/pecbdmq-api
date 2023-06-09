package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_convocatoria_requisito")
public class ConvocatoriaRequisito {
	
	@Id
	@Column(name = "cod_convocatoria")
	private Integer codConvocatoria;
	@Id
	@Column(name = "cod_requisito")
	private Integer cod_requisito;

}
