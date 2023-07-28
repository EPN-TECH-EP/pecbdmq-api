package epntech.cbdmq.pe.dominio.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "esp_catalogo_cursos")
@SQLDelete(sql = "UPDATE {h-schema}esp_catalogo_cursos SET estado = 'ELIMINADO' WHERE cod_catalogo_cursos = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class CatalogoCurso {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_catalogo_cursos")
	private Integer codCatalogoCursos;
	@NotBlank(message = "El atributo 'nombreCatalogoCurso' es obligatorio")
	@Column(name = "nombre_catalogo_curso")
	private String nombreCatalogoCurso;
	@NotBlank(message = "El atributo 'descripcionCatalogoCurso' es obligatorio")
	@Column(name = "descripcion_catalogo_curso")
	private String descripcionCatalogoCurso;
	@NotNull(message = "El atributo 'codTipoCurso' es obligatorio")
	@Column(name = "cod_tipo_curso")
	private Integer codTipoCurso;
	@NotBlank(message = "El atributo 'estado' es obligatorio")
	@Column(name = "estado")
	private String estado;
	
	
	
}