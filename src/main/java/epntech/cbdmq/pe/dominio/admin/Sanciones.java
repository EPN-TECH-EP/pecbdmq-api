package epntech.cbdmq.pe.dominio.admin;

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
@Entity(name = "gen_sancion")
@Table(name = "gen_sancion")
@SQLDelete(sql = "UPDATE {h-schema}gen_sancion SET estado = 'ELIMINADO' WHERE cod_sancion = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Sanciones {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_sancion")
	private Integer cod_sancion;
	@Column(name = "cod_baja")
	private Integer cod_baja;
	@Column(name = "cod_modulo")
	private Integer cod_modulo;
	@Column(name = "cod_documento")
	private Integer cod_documento;
	@Column(name = "cod_estudiante")
	private Integer cod_estudiante;
	@Column(name = "cod_tipo_sancion")
	private Integer cod_tipo_sancion;
	@Column(name = "oficial_semana")
	private String oficialsemana;
	@Column(name = "fecha_sancion")
	private LocalDateTime fechasancion;
	@Column(name = "observacion_sancion")
	private String observacionsancion;
	@Column(name = "ruta_adjunto_sancion")
	private String rutaadjuntosancion;
	@Column(name = "estado")
	private String estado;
	
}
