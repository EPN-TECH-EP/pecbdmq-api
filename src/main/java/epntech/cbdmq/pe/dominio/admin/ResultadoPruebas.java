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
@Table(name = "gen_resultado_prueba")
@SQLDelete(sql = "UPDATE {h-schema}gen_resultado_prueba SET estado = 'ELIMINADO' WHERE cod_resul_prueba = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ResultadoPruebas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_resul_prueba")
	private Integer codResulPrueba;
	
	@Column(name = "cod_funcionario")
	private Integer codFuncionario;
	
	@Column(name = "cod_estudiante")
	private Integer codEstudiante;
	
	@Column(name = "cod_modulo")
	private Integer codModulo;
	
	@Column(name = "cod_postulante")
	private Integer codPostulante;
	
	@Column(name = "cod_periodo_evaluacion")
	private Integer codPeriodoEvaluacion;
	
	@Column(name = "cod_personal_ope")
	private Integer codPersonalOpe;
	
	@Column(name = "cod_prueba")
	private Integer codPrueba;
	
	@Column(name = "cod_parametriza_fisica")
	private Integer codParametrizaFisica;
	
	@Column(name = "resultado")
	private Integer resultado;
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "cumple_prueba")
	private Boolean cumplePrueba;
	
	@Column(name = "nota_promedio_final")
	private Double notaPromedioFinal;
	
	@Column(name = "seleccionado_formacion")
	private Boolean seleccionadoFormacion;
	
}
