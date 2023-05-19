package epntech.cbdmq.pe.dominio.admin;

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
	
	@Column(name = "cod_instructor")
	private Integer codInstructor;
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "cod_materia")
	private Integer codMateria;
	
	@Column(name = "cod_estudiante")
	private Integer codEstudiante;
	
	@Column(name = "cod_periodo_academico")
	private Integer codPeriodoAcademico;
	
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
	
	@Column(name = "nota_supletorio")
	private Double notaSupletorio;
}
