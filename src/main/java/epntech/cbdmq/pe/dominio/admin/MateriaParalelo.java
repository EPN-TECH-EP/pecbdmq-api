package epntech.cbdmq.pe.dominio.admin;

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

@Entity
@Table(name ="gen_materia")
public class MateriaParalelo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_materia")
	private Integer codMateria;
	
	@Column(name = "nombre_materia")
	private String nombreMateria;
	
	@Column(name = "num_horas")
	private Integer numHoras;
	
	@Column(name = "tipo_materia")
	private String tipoMateria;
	
	@Column(name = "observacion_materia")
	private String observacionMateria;
	
	@Column(name = "peso_materia")
	private Integer pesoMateria;
	
	@Column(name = "nota_minima")
	private Integer notaMinima;
	
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "gen_materia_paralelo",
            joinColumns = @JoinColumn(name = "cod_materia"),
            inverseJoinColumns = @JoinColumn(name = "cod_paralelo")
    )
	private List<Paralelo> paralelos = new ArrayList<>();
}
