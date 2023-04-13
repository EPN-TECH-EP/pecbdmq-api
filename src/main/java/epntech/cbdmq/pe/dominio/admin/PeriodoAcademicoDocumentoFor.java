package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_periodo_academico_documento")
public class PeriodoAcademicoDocumentoFor {

	@Id
	private Integer cod_periodo_academico;
	@Id
	private Integer cod_documento;
}
