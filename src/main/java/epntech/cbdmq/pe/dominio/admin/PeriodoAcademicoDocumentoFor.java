package epntech.cbdmq.pe.dominio.admin;

import epntech.cbdmq.pe.dominio.util.PeriodoAcademicoDocumentoForId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_periodo_academico_documento")
@IdClass(PeriodoAcademicoDocumentoForId.class)
public class PeriodoAcademicoDocumentoFor {

	@Id
	@Column(name = "cod_periodo_academico")
	private Integer codPeriodoAcademico;
	@Id
	@Column(name = "cod_documento")
	private Integer codDocumento;
}
