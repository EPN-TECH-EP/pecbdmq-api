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
	private Integer codBaja;
	@Column(name = "cod_modulo")
	private Integer codModulo;
	@Column(name = "cod_tipo_baja")
	private Integer codTipoBaja;
	@Column(name = "fecha_baja")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime fechaBaja;
	@Column(name = "descripcion_baja")
	private String descripcionBaja;
	@Column(name = "usuario_crea_baja")
	private String nombre;
	@Column(name = "fecha_crea_baja")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime fechaCreaBaja;
	@Column(name = "hora_crea_baja")
	private LocalTime horaCreaBaja;
	@Column(name = "usuario_mod_baja")
	private String usuarioModBaja;
	@Column(name = "fecha_mod_baja")
	private LocalDateTime fechaModBaja;
	@Column(name = "hora_mod_baja")
	private LocalTime horaModBaja;
	
	@Column(name = "estado")
	private String estado;
}
