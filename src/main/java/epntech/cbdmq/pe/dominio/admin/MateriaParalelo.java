package epntech.cbdmq.pe.dominio.admin;

import java.math.BigDecimal;
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
@Table(name ="gen_materia_paralelo")
public class MateriaParalelo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_materia_paralelo")
	private Integer codMateriaParalelo;
	@Column(name = "cod_materia_periodo")
	private Integer codMateriaPeriodo;
	@Column(name = "cod_paralelo")
	private Integer codParalelo;
	@Column(name = "estado")
	private String estado;

}
