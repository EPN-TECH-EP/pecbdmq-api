package epntech.cbdmq.pe.dominio.admin;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "gen_catalogo_pregunta")
public class CatalogoRespuesta {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_catalogo_respuesta")
	private Integer cod_catalogo_respuesta;
	@Column(name = "respuesta")
	private String respuesta;
	@Column(name = "estado")
	private String estado;
	
}
