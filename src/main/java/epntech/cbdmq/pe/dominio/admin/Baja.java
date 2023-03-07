package epntech.cbdmq.pe.dominio.admin;


import java.sql.Time;
import java.time.LocalDateTime;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
	@Column(name = "cod_modulo")
	private Integer cod_modulo;
	@Column(name = "cod_tipo_baja")
	private Integer cod_tipo_baja;
	@Column(name = "fecha_baja")
	private LocalDateTime fechabaja;
	@Column(name = "descripcion_baja")
	private String descripcionbaja;
	@Column(name = "usuario_crea_baja")
	private String usuariocreabaja;
	@Column(name = "fecha_crea_baja")
	private LocalDateTime fechacreabaja;
	@Column(name = "hora_crea_baja")
	private Time horacreabaja;
	@Column(name = "usuario_mod_baja")
	private String usuariomodbaja;
	@Column(name = "fecha_mod_baja")
	private LocalDateTime fechamodbaja;
	@Column(name = "hora_mod_baja")
	private Time horamodbaja;
	@Column(name = "ruta_adjunto_baja")
	private String rutaadjuntobaja;
	@Column(name = "estado")
	private String estado;
}
