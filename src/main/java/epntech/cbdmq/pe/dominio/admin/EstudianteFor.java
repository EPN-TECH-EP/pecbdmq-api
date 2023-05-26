package epntech.cbdmq.pe.dominio.admin;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_estudiante")
@SQLDelete(sql = "UPDATE {h-schema}gen_estudiante SET estado = 'ELIMINADO' WHERE cod_estudiante = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class EstudianteFor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_estudiante")
	private Integer codEstudiante;
	
	@Column(name = "cod_datos_personales")
	private Integer codDatosPersonales;
	
	@Column(name = "cod_modulo")
	private Integer codModulo;
	
	@Column(name = "cod_unico_estudiante")
	private String codUnicoEstudiante;
	
	@Column(name = "estado")
	private String estado;
	
	
}
