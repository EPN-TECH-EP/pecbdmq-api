package epntech.cbdmq.pe.dominio.admin.especializacion;

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
@Table(name = "esp_tipo_curso")
@SQLDelete(sql = "UPDATE {h-schema}esp_tipo_curso SET estado = 'ELIMINADO' WHERE cod_tipo_curso = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class TipoCurso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_tipo_curso")
	private Long codTipoCurso;
	
	@Column(name = "nombre_tipo_curso")
	private String nombreTipoCurso;
	
	@Column(name = "estado")
	private String estado;

}
