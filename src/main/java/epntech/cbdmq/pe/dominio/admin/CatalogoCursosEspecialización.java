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
@Table(name = "esp_catalogo_cursos")
@SQLDelete(sql = "UPDATE {h-schema}gen_aula SET estado = 'ELIMINADO' WHERE cod_aula = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class CatalogoCursosEspecialización {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_catalogo_cursos")
	private Long cod_catalogo_cursos;
	
	@Column(name = "nombre_catalogo_curso")
	private String nombre;
	
	@Column(name = "descripcion_catalogo_curso")
	private String descripcion_catalogo_curso;
	
	@Column(name = "estado")
	private String estado;
	
	
	
}
