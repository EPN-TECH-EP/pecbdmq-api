package epntech.cbdmq.pe.dominio.admin;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "gen_parametriza_prueba_resumen")
@SQLDelete(sql = "UPDATE {h-schema}gen_parametriza_prueba_resumen SET estado = 'ELIMINADO' WHERE cod_parametriza_prueba_resumen = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ParametrizaPruebaResumen {
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
	
	
	
	
	
	
	
	
	

}
