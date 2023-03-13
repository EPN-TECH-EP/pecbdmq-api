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

@Entity(name = "pro_semestre")
@Table(name = "pro_semestre")
@SQLDelete(sql = "UPDATE {h-schema}pro_semestre SET estado = 'ELIMINADO' WHERE cod_semestre = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Semestre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_semestre")
	private Integer codSemestre;
	
	@Column(name = "semestre")
	private String semestre;
	
	@Column(name = "estado")
	private String estado;
}
