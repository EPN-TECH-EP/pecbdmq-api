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
@Table(name = "esp_curso_estado")
@SQLDelete(sql = "UPDATE {h-schema}esp_curso_estado SET estado = 'ELIMINADO' WHERE cod_curso_estado = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class CursoEstado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_curso_estado")
	private Long codCursoEstado;
	
	@Column(name = "cod_catalogo_estados")
	private Long codCatalogoEstados;
	
	@Column(name = "cod_curso_especializacion")
	private Long codCursoEspecializacion;
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "orden")
	private Integer orden;
}
