package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "gen_catalogo_pregunta")
public class CatalogoPreguntas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include()
	@Column(name = "cod_catalogo_pregunta")
	private Integer cod_catalogo_pregunta;
	
	@Column(name = "pregunta")
	private String pregunta;
	
	@Column(name = "estado")
	private String estado;
	
	
}
