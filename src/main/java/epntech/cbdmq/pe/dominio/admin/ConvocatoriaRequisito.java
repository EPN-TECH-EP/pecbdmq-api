package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_convocatoria_requisito")
public class ConvocatoriaRequisito {
	
	@Id
	private Integer cod_convocatoria;
	@Id
	private Integer cod_requisito;

}
