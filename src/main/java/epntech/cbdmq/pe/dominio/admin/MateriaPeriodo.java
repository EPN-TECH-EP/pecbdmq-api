package epntech.cbdmq.pe.dominio.admin;

import java.math.BigDecimal;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity(name="MateriaPeriodo")
@Table(name = "gen_materia_periodo")
public class MateriaPeriodo {

	
	
	@Id
	@Column(name = "cod_periodo_academico")
	private Integer codPeriodoAcademico;
	@Column(name = "cod_materia")
	private Integer codMateria;
	@Column(name = "nota_minima")
	private BigDecimal notaMinima;
	@Column(name = "numero_horas")
	private Integer numeroHoras;
	@Column(name = "peso_materia")
	private BigDecimal pesoMateria;
	@Column(name = "nota_minima_supletorio_inicio")
	private BigDecimal notaMinimaSupletorioInicio;
	@Column(name = "nota_minima_supletorio_fin")
	private BigDecimal notaMinimaSupletorioFin;
	
	/*@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "gen_materia_periodo",
            joinColumns = @JoinColumn(name = "cod_materia"),
            inverseJoinColumns = @JoinColumn(name = "cod_periodo_academico")
    )
	private List<Paralelo> MateriPeriodo = new ArrayList<>();*/
	
	
	
	
}
