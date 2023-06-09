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
@Table(name = "gen_falta_periodo")
public class FaltaPeriodo {
	@Id
	
	@Column(name = "cod_falta_periodo")
	private Integer codFaltaPeriodo;
	
	/*@Column(name = "cod_falta")
	private Integer cod_falta;*/
	
	@Column(name = "cod_periodo_academico")
	private Integer codPeriodoAcademico;
	
	@Column(name = "puntaje")
	private BigDecimal puntaje;
	
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "gen_falta_periodo",
            joinColumns = @JoinColumn(name = "cod_falta_periodo"),
            inverseJoinColumns = @JoinColumn(name = "cod_falta")
    )
	private List<Falta> falta = new ArrayList<>();
	
}
