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
	private Integer codPonderacion;
	@Column(name = "cod_modulo")
	private Integer codModulo;
	@Column(name = "cod_periodo_academico")
	private Integer codPeriodoAcademico;
	@Column(name = "cod_componente_nota")
	private Integer codComponenteNota;
	//@Column(name = "cod_tipo_nota")
	//private Integer codTipoNota;
	@Column(name = "porcentaje_final_ponderacion")
	private BigDecimal porcentajeFinalPonderacion;
	//@Column(name = "porcentaje_nota_materia")
	//private BigDecimal porcentajeNotaMateria;
	//@Column(name = "fecha_inicio_vigencia")
	//private LocalDateTime fechaInicioVigencia;
	///@Column(name = "fecha_fin_vigencia")
	//private LocalDateTime fechaFinVigencia;
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "cod_materia")
	private Long codMateria;
}
