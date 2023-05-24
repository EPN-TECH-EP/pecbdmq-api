package epntech.cbdmq.pe.dominio.admin;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity 
@Table(name = "gen_materia_periodo")
public class MateriaPeriodo {

	
	
	@Id
	@Column(name = "cod_periodo_academico")
	private Integer cod_periodo_academico;
	@Column(name = "cod_materia")
	private Integer cod_materia;
	@Column(name = "nota_minima")
	private BigDecimal nota_minima;
	@Column(name = "numero_horas")
	private Integer numero_horas;
	@Column(name = "peso_materia")
	private BigDecimal peso_materia;
	@Column(name = "nota_minima_supletorio_inicio")
	private BigDecimal nota_minima_supletorio_inicio;
	@Column(name = "nota_minima_supletorio_fin")
	private BigDecimal nota_minima_supletorio_fin;
	
	/*@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "gen_materia_periodo",
            joinColumns = @JoinColumn(name = "cod_materia"),
            inverseJoinColumns = @JoinColumn(name = "cod_periodo_academico")
    )
	private List<Paralelo> MateriPeriodo = new ArrayList<>();*/
	
	
	
	
}
