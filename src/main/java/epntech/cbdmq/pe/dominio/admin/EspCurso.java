package epntech.cbdmq.pe.dominio.admin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import lombok.Data;

@Data

@Entity(name = "esp_curso")
@Table(name = "esp_curso")

public class EspCurso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_curso_especializacion")
	private Integer codcursoespecializacion;
	@Column(name = "nombre_curso_especializacion")
	private String nombrecursoespecializacion;
	@Column(name = "numero_cupo")
	private Integer numerocupo;
	@Column(name = "adjunto_planificacion")
	private String adjuntoplanificacion;
	@Column(name = "tipo_curso")
	private String tipocurso;
	@Column(name = "fecha_inicio_curso")
	private LocalDateTime fechainiciocurso;
	@Column(name = "fecha_fin_curso")
	private LocalDateTime fechafincurso;
	@Column(name = "fecha_inicio_carga_nota")
	private LocalDateTime fechainiciocarganota;
	@Column(name = "fecha_fin_carga_nota")
	private LocalDateTime fechafincarganota;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "gen_paralelo_curso", joinColumns = @JoinColumn(name = "cod_curso_especializacion"), 
	inverseJoinColumns = @JoinColumn(name = "cod_paralelo"))
	private List<Paralelo> paralelos = new ArrayList<>();
}
