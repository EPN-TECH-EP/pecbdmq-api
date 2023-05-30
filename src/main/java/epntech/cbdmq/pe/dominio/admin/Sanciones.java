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
@Entity(name = "gen_sancion")
@Table(name = "gen_sancion")
@SQLDelete(sql = "UPDATE {h-schema}gen_sancion SET estado = 'ELIMINADO' WHERE cod_sancion = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Sanciones {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_sancion")
	private Integer codSancion;
	@Column(name = "cod_documento")
	private Integer codDocumento;
	@Column(name = "cod_estudiante")
	private Integer codEstudiante;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "fecha_sancion")
	private Date fechaSancion;
	@Column(name = "observacion_sancion")
	private String observacionSancion;
	@Column(name = "estado")
	private String estado;
	@Column(name = "cod_instructor")
	private Integer codInstructor;
	@Column(name = "cod_falta_periodo")
	private Integer codFaltaPeriodo;
	@Column(name = "cod_falta_semestre")
	private Integer codFaltaSemestre;
	@Column(name = "cod_falta_curso")
	private Integer codFaltaCurso;
	
}
