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
import lombok.*;

@Data

@Entity(name = "gen_instructor")
@Table(name = "gen_instructor")
public class Instructor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_instructor")
	private Integer codInstructor;
	@Column(name = "cod_datos_personales")
	private Integer codDatosPersonales;
	@Column(name = "cod_tipo_procedencia")
	private Integer codTipoProcedencia;
	/*@Column(name = "cod_tipo_instructor")
	private Integer cod_tipo_instructor;
	@Column(name = "cod_periodo_academico")
	private Integer cod_periodo_academico;
	@Column(name = "cod_periodo_evaluacion")
	private Integer cod_periodo_evaluacion;*/
	
	
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "gen_instructor_periodo",
            joinColumns = @JoinColumn(name = "cod_instructor"),
            inverseJoinColumns = @JoinColumn(name = "cod_periodo_academico")
    )
	private List<PeriodoAcademico> PeriodoAcademico = new ArrayList<>();
}
