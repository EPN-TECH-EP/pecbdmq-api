package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data

@Entity(name = "gen_pamtriza_pru_fisica")
@Table(name = "gen_pamtriza_pru_fisica")
public class PruebaFisica {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_parametriza_fisica")
	private Integer cod_parametriza_fisica;
	@Column(name = "edad_inicio")
	private Integer edad_inicio;
	@Column(name = "edad_fin")
	private Integer edad_fin;
	@Column(name = "sexo")
	private String sexo;
	@Column(name = "calificacion")
	private Integer calificacion;
	@Column(name = "peso_prueba")
	private Integer peso_prueba;
	@Column(name = "valor")
	private Integer valor;
	@Column(name = "no_min_flexion")
	private Integer no_min_flexion;
	
	
	
}
