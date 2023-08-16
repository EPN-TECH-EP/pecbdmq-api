package epntech.cbdmq.pe.dominio;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProfesionalizacionEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_parametro")
public class Parametro extends ProfesionalizacionEntity {

	@Id
	@Column(name = "cod_parametro")
	private Long codParametro;
	@Column(name = "nombre_parametro")
	private String nombreParametro;
	@Column(name = "valor")
	private String valor;
}
