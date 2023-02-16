package epntech.cbdmq.pe.dominio.admin;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "gen_periodo_academico")
@Table(name = "gen_periodo_academico")
public class PeriodoAcademico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_periodo_academico")
	private Integer codigo;
	
	@Column(name = "cod_modulo")
	private Integer modulo;
	
	@Column(name = "cod_semestre")
	private Integer semestre;
	
	@Column(name = "fecha_inicio_periodo_acad")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime fechaInicio;
	
	@Column(name = "fecha_fin_periodo_acad")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime fechaFin;
	

}
