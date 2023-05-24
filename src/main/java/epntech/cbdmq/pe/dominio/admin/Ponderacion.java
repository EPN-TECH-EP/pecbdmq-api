package epntech.cbdmq.pe.dominio.admin;

import java.math.BigDecimal;
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
@Entity(name = "gen_ponderacion")
@Table(name = "gen_ponderacion")
@SQLDelete(sql = "UPDATE {h-schema}gen_ponderacion SET estado = 'ELIMINADO' WHERE cod_ponderacion = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Ponderacion {

	
	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_ponderacion")
	private Integer cod_ponderacion;
	@Column(name = "cod_modulo")
	private Integer cod_modulo;
	@Column(name = "cod_periodo_academico")
	private Integer cod_periodo_academico;
	@Column(name = "cod_componente_nota")
	private Integer cod_componente_nota;
	@Column(name = "cod_tipo_nota")
	private Integer cod_tipo_nota;
	@Column(name = "porcentaje_final_ponderacion")
	private BigDecimal porcentaje_final_ponderacion;
	@Column(name = "porcentaje_nota_materia")
	private BigDecimal porcentajenotamateria;
	@Column(name = "fecha_inicio_vigencia")
	private LocalDateTime fechainiciovigencia;
	@Column(name = "fecha_fin_vigencia")
	private LocalDateTime fechafinvigencia;
	@Column(name = "estado")
	private String estado;
	
}
