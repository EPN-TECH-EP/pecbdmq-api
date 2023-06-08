package epntech.cbdmq.pe.dominio.admin;

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
public class CatalogoCurso {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_catalogo_cursos")
	private Integer cod_catalogo_cursos;
	@Column(name = "nombre_catalogo_curso")
	private String nombre;
	@Column(name = "descripcion_catalogo_curso")
	private String descripcion;
	@Column(name = "estado")
	private String estado;
	
	
	
}
