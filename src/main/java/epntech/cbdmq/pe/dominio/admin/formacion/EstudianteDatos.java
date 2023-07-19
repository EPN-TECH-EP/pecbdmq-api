package epntech.cbdmq.pe.dominio.admin.formacion;

import java.util.Date;

import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

@Data
public class EstudianteDatos{

	private Integer codNota;
	private String codUnicoEstudiante;
	private String cedula;
	private String nombreCompleto;
	private Double notaFinal;
	private Double notaDisciplina;
	private Double notaSupletorio;
	private Integer codParalelo;
	private String nombreParalelo;


	public EstudianteDatos(Integer codNota, String codUnicoEstudiante, String cedula, String nombreCompleto, Double notaFinal, Double notaDisciplina, Double notaSupletorio, Integer codParalelo, String nombreParalelo) {
		this.codNota = codNota;
		this.codUnicoEstudiante= codUnicoEstudiante;
		this.cedula=cedula;
		this.nombreCompleto = nombreCompleto;
		this.notaFinal = notaFinal;
		this.notaDisciplina = notaDisciplina;
		this.notaSupletorio = notaSupletorio;
		this.codParalelo = codParalelo;
		this.nombreParalelo = nombreParalelo;
	}
}
