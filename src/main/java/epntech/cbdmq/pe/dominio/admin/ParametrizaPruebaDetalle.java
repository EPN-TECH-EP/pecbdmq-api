package epntech.cbdmq.pe.dominio.admin;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "gen_parametriza_prueba_detalle")
@SQLDelete(sql = "UPDATE {h-schema}gen_parametriza_prueba_detalle SET estado = 'ELIMINADO' WHERE cod_parametriza_prueba_detalle = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ParametrizaPruebaDetalle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include()
	@Column(name = "cod_parametriza_prueba_detalle")
	private Integer cod_parametriza_prueba_detalle;
	@Column(name = "edad_inicio_meses")
	private Integer edad_inicio_meses;
	@Column(name = "edad_fin_meses")
	private Integer edad_fin_meses;
	@Column(name = "sexo")
	private String sexo;
	@Column(name = "calificacion")
	private Integer calificacion;
	@Column(name = "estado")
	private String estado;
	@Column(name = "cod_parametriza_prueba_resumen")
	private Integer cod_parametriza_prueba_resumen;
	/*@Column(name = "numero _repeticiones")
	private BigDecimal numero_repeticiones;*/
	@Column(name = "minutos_segundos")
	private LocalDateTime minutos_segundos;
	@Column(name = "cod_subtipo_prueba")
	private Integer cod_subtipo_prueba;
	
	@OneToMany(mappedBy="cod_parametriza_prueba_resumen")
    private List<ParametrizaPruebaResumen> Pruebaresumen;
	

	@OneToMany(mappedBy="cod_subtipo_prueba")
    private List<SubTipoPrueba> Subtipoprueba;
	  
	

	
}
