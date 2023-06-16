package epntech.cbdmq.pe.dominio.admin;


import java.util.Date;
import java.util.List;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	@Column(name = "cod_tipo_baja")
	private Integer codTipoBaja;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "fecha_baja_actual")
	private Date fechaBajaActual;
	@Column(name = "descripcion_baja")
	private String descripcionBaja;
	@Column(name = "estado")
	private String estado;
	@Column(name = "cod_estudiante")
	private Integer codEstudiante;
	@Column(name = "cod_periodo_academico")
	private Integer codPeriodoAcademico;
	@Column(name = "codSemestre")
	private Integer codSemetre;
	@Column(name = "codSancion")
	private Integer codSancion;
	
	@OneToMany(mappedBy = "codBaja", cascade = CascadeType.ALL)
	private List<BajaDocumento> documentos;
	
}
