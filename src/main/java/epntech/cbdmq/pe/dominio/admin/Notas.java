package epntech.cbdmq.pe.dominio.admin;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

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
@Entity(name = "gen_nota")
@Table(name = "gen_nota")
@SQLDelete(sql = "UPDATE {h-schema}gen_nota SET estado = 'ELIMINADO' WHERE cod_nota_formacion = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Notas {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_nota_formacion")
	private Integer codNotaFormacion;
	@Column(name = "cod_instructor")
	private Integer codInstructor;
	@Column(name = "cod_curso_especializacion")
	private Integer codCursoEspecializacion;
	@Column(name = "cod_ponderacion")
	private Integer codPonderacion;
	@Column(name = "cod_estudiante")
	private Integer codEstudiante;
	@Column(name = "cod_materia")
	private Integer codMateria;
	@Column(name = "aporte_examen")
	private BigDecimal aporteExamen;
	@Column(name = "aporte_academico")
	private BigDecimal aporteAcademico;
	@Column(name = "nota_final_formacion")
	private BigDecimal notaFinalFormacion;
	@Column(name = "usuario_crea_nota")
	private String usuarioCreaNota;
	@Column(name = "fecha_crea_nota")
	private LocalDateTime fechaCreaNota;
	@Column(name = "hora_crea_nota")
	private Time horaCreaNota;
	@Column(name = "usuario_mod_nota")
	private String usuarioModNota;
	@Column(name = "fecha_mod_nota")
	private Date fechaModNota;
	@Column(name = "hora_mod_nota")
	private Time horaModNota;
	@Column(name = "estado")
	private String estado;
	
	
	/*@Column(name = "cod_nota_formacion")
	private Integer cod_nota_formacion;
	@Column(name = "cod_instructor")
	private Integer cod_instructor;
	@Column(name = "cod_curso_especializacion")
	private Integer cod_curso_especializacion;
	@Column(name = "cod_ponderacion")
	private Integer cod_ponderacion;
	@Column(name = "cod_estudiante")
	private Integer cod_estudiante;
	@Column(name = "cod_materia")
	private Integer cod_materia;
	@Column(name = "aporte_academico")
	private Integer aporte_academico;
	@Column(name = "nota_final_formacion")
	private UNSUPPORTED_TYPE nota_final_formacion;
	@Column(name = "usuario_crea_nota")
	private String usuario_crea_nota;
	@Column(name = "fecha_crea_nota")
	private LocalDateTime fecha_crea_nota;
	@Column(name = "hora_crea_nota")
	private UNSUPPORTED_TYPE hora_crea_nota;
	@Column(name = "usuario_mod_nota")
	private String usuario_mod_nota;
	@Column(name = "fecha_mod_nota")
	private LocalDateTime fecha_mod_nota;
	@Column(name = "hora_mod_nota")
	private UNSUPPORTED_TYPE hora_mod_nota;
	@Column(name = "estado")
	private String estado;
	@Column(name = "resultado")
	private UNSUPPORTED_TYPE resultado;*/
	
}
