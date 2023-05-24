package epntech.cbdmq.pe.dominio.admin;

import java.math.BigDecimal;
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


@Data
@Entity(name = "gen_apelacion")
@Table(name = "gen_apelacion")
@SQLDelete(sql = "UPDATE {h-schema}gen_apelacion SET estado = 'ELIMINADO' WHERE cod_apelacion = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Apelacion {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_apelacion")
	private Integer cod_apelacion;
	/*@Column(name = "cod_modulo")
	private Integer cod_modulo;
	@Column(name = "cod_estudiante")
	private Integer cod_estudiante;
	@Column(name = "cod_instructor")
	private Integer cod_instructor;*/
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "fecha_solicitud")
	private LocalDateTime fechasolicitud;
	@Column(name = "nombre_prueba_revision")
	private String nombre;
	@Column(name = "observacion_aspirante")
	private String observacionaspirante;
	@Column(name = "observacion_docente")
	private String observaciondocente;
	@Column(name = "aprobacion")
	private String aprobacion;
	@Column(name = "nota_actual")
	private BigDecimal notaactual;
	@Column(name = "nota_nueva")
	private BigDecimal notanueva;
	@Column(name = "respuesta")
	private String respuesta;
	@Column(name = "estado")
   	private String estado;
	
	
}
