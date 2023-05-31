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
@Table(name = "gen_postulante")
@SQLDelete(sql = "UPDATE {h-schema}gen_postulante SET estado = 'ELIMINADO' WHERE cod_postulante = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "coalesce(estado, '') <> 'ELIMINADO'")
public class Postulante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_postulante")
	private Long codPostulante;
	
	@Column(name = "cod_datos_personales")
	private Integer codDatoPersonal;
	
	@Column(name = "id_postulante")
	private String idPostulante;
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "cod_usuario")
	private Integer codUsuario;
	
	@Column(name = "cod_periodo_academico")
	private Integer codPeriodoAcademico;
}
