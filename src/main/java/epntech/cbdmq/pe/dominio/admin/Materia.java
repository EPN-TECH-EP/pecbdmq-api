package epntech.cbdmq.pe.dominio.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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

@Entity(name = "gen_materia")
@Table(name ="gen_materia")
@SQLDelete(sql = "UPDATE {h-schema}gen_materia SET estado = 'ELIMINADO' WHERE cod_materia = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Materia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_materia")
	private Integer codMateria;
	@Column(name = "nombre_materia")
	private String nombre;
	//@Column(name = "num_horas")
	//private Integer numHoras;
	@Column(name = "tipo_materia")
	private String tipoMateria;
	//@Column(name = "observacion_materia")
	//private String observacionMateria;
	//@Column(name = "peso_materia")
	//private BigDecimal pesoMateria;
	//@Column(name = "nota_minima")
	//private BigDecimal notaMinima;
	@Column(name = "estado")
	private String estado;
	
	//@ManyToMany(mappedBy = "materias", cascade = CascadeType.ALL)
	//public Set<Aula> aulas;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "gen_materia_paralelo",
            joinColumns = @JoinColumn(name = "cod_materia"),
            inverseJoinColumns = @JoinColumn(name = "cod_paralelo")
    )
	private List<Paralelo> paralelos = new ArrayList<>();
}
