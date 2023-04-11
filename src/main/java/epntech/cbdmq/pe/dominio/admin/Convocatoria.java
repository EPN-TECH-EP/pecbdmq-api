package epntech.cbdmq.pe.dominio.admin;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "gen_convocatoria")
@Table(name = "gen_convocatoria")
@SQLDelete(sql = "UPDATE {h-schema}gen_convocatoria SET estado_convocatoria = 'ELIMINADO' WHERE cod_convocatoria = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado_convocatoria <> 'ELIMINADO'")
public class Convocatoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include()
	@Column(name = "cod_convocatoria")
	private Integer codConvocatoria;
	
	@Column(name = "cod_periodo_evaluacion")
	private Integer codPeriodoEvaluacion;
	
	@Column(name = "cod_periodo_academico")
	private Integer codPeriodoAcademico;
	
	@Column(name = "cod_modulo")
	private Integer codModulo;
	
	@Column(name = "nombre_convocaria")
	private String nombre;
	
	@Column(name = "estado_convocatoria")
	private String estado;
	
	@Column(name = "fecha_inicio_convocatoria")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicioConvocatoria;
	
	@Column(name = "fecha_fin_convocatoria")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fechaFinConvocatoria;
	
	@Column(name = "hora_inicio_convocatoria")
	@JsonFormat(pattern = "HH:mm")
	private LocalTime horaInicioConvocatoria;
	
	@Column(name = "hora_fin_convocatoria")
	@JsonFormat(pattern = "HH:mm")
	private LocalTime horaFinConvocatoria;
	
	

	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "gen_convocatoria_documento",
            joinColumns = @JoinColumn(name = "cod_convocatoria"),
            inverseJoinColumns = @JoinColumn(name = "cod_documento")
    )
	private List<ConvocatoriaDocumento> documentos = new ArrayList<>();
}
