package epntech.cbdmq.pe.dominio.admin;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;


@Data
@Entity(name = "gen_resultado_prueba")
@Table(name = "gen_resultado_prueba")
@SQLDelete(sql = "UPDATE {h-schema}gen_resultado_prueba SET estado = 'ELIMINADO' WHERE cod_resul_prueba = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")

public class ResultadoPrueba {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_resul_prueba")
	private Integer cod_resul_prueba;
	@Column(name = "cod_funcionario")
	private Integer cod_funcionario;
	@Column(name = "cod_estudiante")
	private Integer cod_estudiante;
	@Column(name = "cod_modulo")
	private Integer cod_modulo;
	@Column(name = "cod_postulante")
	private Integer cod_postulante;
	@Column(name = "cod_periodo_evaluacion")
	private Integer cod_periodo_evaluacion;
	@Column(name = "cod_personal_ope")
	private Integer cod_personal_ope;
	@Column(name = "cod_prueba")
	private Integer cod_prueba;
	@Column(name = "cod_parametriza_fisica")
	private Integer cod_parametriza_fisica;
	@Column(name = "cod_tipo_prueba")
	private Integer cod_tipo_prueba;
	@Column(name = "resultado")
	private Integer resultado;
	@Column(name = "cumple_prueba")
	private String cumpleprueba;
	@Column(name = "estado")
	private String estado;
	
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "epo_personal_operativo",
            joinColumns = @JoinColumn(name = "cod_parametriza_fisica"),
            inverseJoinColumns = @JoinColumn(name = "cod_datos_personales")
    )
	private List<PruebaFisica> Pruebafisca = new ArrayList<>();
	
	
}
