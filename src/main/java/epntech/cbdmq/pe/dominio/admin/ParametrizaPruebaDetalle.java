package epntech.cbdmq.pe.dominio.admin;


import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private Integer codParametrizaPruebaDetalle;
	@Column(name = "edad_inicio_meses")
	private Integer edadInicioMeses;
	@Column(name = "edad_fin_meses")
	private Integer edadFinMeses;
	@Column(name = "sexo")
	private String sexo;
	@Column(name = "calificacion")
	private Integer calificacion;
	@Column(name = "estado")
	private String estado;
	@Column(name = "cod_parametriza_prueba_resumen")
	private Integer codParametrizaPruebaResumen;
	@Column(name = "numero_repeticiones")
	private BigInteger numeroRepeticiones;
	@Column(name = "minutos_segundos")
	private LocalTime minutosSegundos;
	@Column(name = "cod_subtipo_prueba")
	private Integer codSubtipoPrueba;
	
	/*@OneToMany(mappedBy="cod_parametriza_prueba_resumen")
    private List<ParametrizaPruebaResumen> Pruebaresumen;*/
	

	@OneToMany(mappedBy="codSubtipoPrueba")
    private List<SubTipoPrueba> subTipoPrueba;
	  
	

	
}
