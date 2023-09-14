package epntech.cbdmq.pe.dominio.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import epntech.cbdmq.pe.dominio.admin.SubTipoPrueba;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "gen_parametriza_prueba_resumen_datos")
@Table(name = "gen_parametriza_prueba_resumen")
public class ParametrizaPruebaResumenDatos {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include()
	@Column(name = "cod_parametriza_prueba_resumen")
	private Integer codParametrizaPruebaResumen;
	
	@Column(name = "fecha_creacion")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaCreacion;
	
	@Column(name = "fecha_inicio")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaInicio;
	
	@Column(name = "fecha_fin")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaFin;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "estado")
	private String estado;
	@Column(name = "ponderacion")
	private BigDecimal ponderacion;
	
	//@OneToMany(mappedBy="codSubtipoPrueba")
    //private List<SubTipoPrueba> codSubTipoPrueba;
	
	private Integer codSubtipoPrueba;
	
	private	String SubtipoPruebaNombre;
	private	String TipoPruebaNombre;
	

}
