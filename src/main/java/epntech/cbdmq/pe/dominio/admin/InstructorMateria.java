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
import epntech.cbdmq.pe.dominio.fichaPersonal.Instructor;

@Data
@Entity
@Table(name = "gen_instructor_materia")
public class InstructorMateria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "cod_instructor")
	private Integer codInstructor;
	
	@Column(name = "cod_materia")
	private Integer codMateria;
	
	@Column(name = "cod_instructor_materia")
	private Integer codInstructorMateria;
	
	@Column(name = "es_coordinador")
	private Boolean esCoordinador;
	
	@Column(name = "es_asistente")
	private Boolean esAsistente;
	
	@Column(name = "cod_periodo_academico")
	private Integer codPeriodoAcademico;
	
	@Column(name = "cod_semestre")
	private Integer codSemestre;
	
	@Column(name = "cod_paralelo")
	private Integer codParalelo;
	
	@Column(name = "cod_aula")
	private Integer codAula;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)

	@JoinTable(name = "gen_instructor_materia", joinColumns = @JoinColumn(name = "cod_instructor_materia"), 
	inverseJoinColumns = @JoinColumn(name = "cod_instructor"))
	private List<Instructor> Instructor = new ArrayList<>();
	
	/*@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "gen_instructor_materia", joinColumns = @JoinColumn(name = "cod_instructor"), 
	inverseJoinColumns = @JoinColumn(name = "cod_materia"))
	private List<Materia> Materia = new ArrayList<>();*/
	
	


	
}

