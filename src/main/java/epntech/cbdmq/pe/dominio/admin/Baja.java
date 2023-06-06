package epntech.cbdmq.pe.dominio.admin;


import java.time.LocalDateTime;
import java.time.LocalTime;

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
@Entity(name = "gen_baja")
@Table(name = "gen_baja")
@SQLDelete(sql = "UPDATE {h-schema}gen_baja SET estado = 'ELIMINADO' WHERE cod_baja = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Baja {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_baja")
	private Integer cod_baja;
	
	@Column(name = "cod_tipo_baja")
	private Integer cod_tipo_baja;
	
	@Column(name = "fecha_baja_actual")
	private LocalDateTime fechabajaactual;
	
	@Column(name = "descripcion_baja")
	private String descripcionbaja;
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "cod_estudiante")
	private Integer codestudiante;
	
	@Column(name = "cod_periodo_academico")
	private Integer codperiodoacademico;
	
	@Column(name = "cod_semestre")
	private Integer codsemestre;
	
	@Column(name = "cod_sancion")
	private Integer cod_sancion;
	
}
