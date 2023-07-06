package epntech.cbdmq.pe.dominio.admin;

import java.time.LocalDateTime;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_nota_formacion")
@SQLDelete(sql = "UPDATE {h-schema}gen_nota_formacion SET estado = 'ELIMINADO' WHERE cod_nota_formacion = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class NotasFormacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_nota_formacion")
	private Integer codNotaFormacion;
	@Column(name = "nota_minima")
	private Double notaMinima;
	@Column(name = "peso_materia")
	private Double pesoMateria;
	@Column(name = "numero_horas")
	private Integer numeroHoras;

	@Column(name = "nota_materia")
	private Double notaMateria;

	@Column(name = "nota_ponderacion")
	private Double notaPonderacion;

	@Column(name = "nota_disciplina")
	private Double notaDisciplina;
	@Column(name = "estado")
	private String estado;

	@Column(name = "nota_supletorio")
	private Double notaSupletorio;

	@Column(name = "fecha_ingreso")
	private LocalDateTime fechaIngreso;
	@Column(name="cod_estudiante_materia_paralelo")
	private Integer codEstudianteMateriaParalelo;
}
	

	

	

