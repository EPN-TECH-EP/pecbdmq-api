package epntech.cbdmq.pe.dominio.admin;

import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;
import jakarta.persistence.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data

@Entity(name = "pro_semestre")
@Table(name = "pro_semestre")
@SQLDelete(sql = "UPDATE {h-schema}pro_semestre SET estado = 'ELIMINADO' WHERE cod_semestre = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Semestre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_semestre")
	private Integer codSemestre;
	
	@Column(name = "semestre")
	private String semestre;
	
	@Column(name = "estado")
	private String estado;

	@Column(name = "fecha_inicio_semestre")
	private Date fechaInicioSemestre;

	@Column(name = "fecha_fin_semestre")
	private Date fechaFinSemestre;

	@Column(name = "descripcion")
	private String descripcion;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "pro_estudiante_semestre", joinColumns = @JoinColumn(name = "cod_semestre"), inverseJoinColumns = @JoinColumn(name = "cod_estudiante"))
	private Set<Estudiante> estudiante;

}
