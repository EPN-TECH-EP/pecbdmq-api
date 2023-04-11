package epntech.cbdmq.pe.dominio.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "gen_periodo_academico")
@Table(name = "gen_periodo_academico")
@SQLDelete(sql = "UPDATE {h-schema}gen_periodo_academico SET estado = 'ELIMINADO' WHERE cod_periodo_academico = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class PeriodoAcademico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include()
	@Column(name = "cod_periodo_academico")
	private Integer codigo;
	
	@Column(name = "cod_modulo_estados")
	private Integer moduloEstados;
	
	@Column(name = "fecha_inicio_periodo_acad")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fechaInicio;
	
	@Column(name = "fecha_fin_periodo_acad")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fechaFin;
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "gen_periodo_academico_documento",
            joinColumns = @JoinColumn(name = "cod_periodo_academico"),
            inverseJoinColumns = @JoinColumn(name = "cod_documento")
    )
	private List<Documento> documentos = new ArrayList<>();

}
