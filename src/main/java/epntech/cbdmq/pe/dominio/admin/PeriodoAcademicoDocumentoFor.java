package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_periodo_academico_documento")
public class PeriodoAcademicoDocumentoFor {

	@Id
	@Column(name = "cod_periodo_academico")
	private Integer cod_periodo_academico;
	@Id
	@Column(name = "cod_documento")
	private Integer cod_documento;
}
