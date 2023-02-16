package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data

@Entity(name = "gen_unidad_gestion")
@Table(name = "gen_unidad_gestion")
public class UnidadGestion {

	@Id
	@jakarta.persistence.GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_unidad_gestion")
	public int codigo;
	
	@Column(name = "unidad_gestion")
	public String nombre;
}
