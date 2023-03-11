package epntech.cbdmq.pe.dominio.admin;

import java.sql.Time;
import java.time.LocalDateTime;

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
	@Column(name = "aporte_examen")
	private Integer aporteexamen;
	@Column(name = "aporte_academico")
	private Integer aporteacademico;
	@Column(name = "nota_final_formacion")
	private Integer notafinalformacion;
	@Column(name = "usuario_crea_nota")
	private String usuariocreanota;
	@Column(name = "fecha_crea_nota")
	private LocalDateTime fechacreanota;
	@Column(name = "hora_crea_nota")
	private Time horacreanota;
	@Column(name = "usuario_mod_nota")
	private String usuariomodnota;
	@Column(name = "fecha_mod_nota")
	private LocalDateTime fechamodnota;
	@Column(name = "hora_mod_nota")
	private Time horamodnota;
	@Column(name = "estado")
	private String estado;
	
}
