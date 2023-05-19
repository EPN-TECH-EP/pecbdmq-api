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
@Table(name = "gen_periodo_academico_documento")
public class PADocumento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_periodo_academico_documento")
	private Integer codPeriodoAcademicoDocumento;
	@Column(name = "cod_periodo_academico")
	private Integer codPeriodoAcademico;
	@Column(name = "cod_documento")
	private Integer codDocumento;
}
