package epntech.cbdmq.pe.dominio.admin;

import epntech.cbdmq.pe.dominio.util.MateriaPeriodoDataId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_materia_periodo")
@IdClass(MateriaPeriodoDataId.class)
public class MateriaPeriodoData {
	
	@Id
	@Column(name = "cod_periodo_academico")
	private Integer codPeriodoAcademico;
	@Id
	@Column(name = "cod_materia")
	private Integer codMateria;
	
	@Column(name = "nota_minima")
	private Double notaMinima;
	
	@Column(name = "numero_horas")
	private Integer numeroHoras;
	
	@Column(name = "peso_materia")
	private Double pesoMateria;

}
