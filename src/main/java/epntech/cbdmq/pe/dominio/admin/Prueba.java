package epntech.cbdmq.pe.dominio.admin;



import java.time.LocalTime;
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
@Entity(name = "gen_prueba")
@Table(name = "gen_prueba")
@SQLDelete(sql = "UPDATE {h-schema}gen_subtipo_prueba SET estado = 'ELIMINADO' WHERE cod_subtipo_prueba = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Prueba {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_prueba")
	private Integer codPrueba;
	
	@Column(name = "cod_subtipo_prueba")
	private Integer codSubtipoPrueba;
	
	@Column(name = "descripcion_prueba")
	private String descripcionPrueba;
	
	@Column(name = "fecha_inicio")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicio;
	
	@Column(name = "fecha_fin")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fechaFin;
	
	@Column(name = "hora")
	@JsonFormat(pattern = "HH:mm")
	private LocalTime hora;
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "cod_periodo_academico")
	private Integer codPeriodoAcademico;
	
	@Column(name = "cod_curso_especializacion")
	private Integer codCursoEspecializacion;
	
}

