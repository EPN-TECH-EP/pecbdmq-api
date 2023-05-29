package epntech.cbdmq.pe.dominio.admin;

import java.util.Date;

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
@Entity
@Table(name = "gen_apelacion")
@SQLDelete(sql = "UPDATE {h-schema}gen_apelacion SET estado = 'ELIMINADO' WHERE cod_apelacion = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Apelacion {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_apelacion")
	private Integer codApelacion;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "fecha_solicitud")
	private Date fechaSolicitud;
	
	@Column(name = "observacion_estudiante")
	private String observacionEstudiante;
	
	@Column(name = "observacion_instructor")
	private String observacionInstructor;
	
	@Column(name = "aprobacion")
	private Boolean aprobacion;
	
	@Column(name = "nota_actual")
	private Double notaActual;
	
	@Column(name = "nota_nueva")
	private Double notaNueva;
	
	@Column(name = "estado")
   	private String estado;
	
	@Column(name = "cod_nota_formacion")
	private Integer codNotaFormacion;
	
	@Column(name = "cod_nota_profesionalizacion")
	private Integer codNotaProfesionalizacion;
	
}
