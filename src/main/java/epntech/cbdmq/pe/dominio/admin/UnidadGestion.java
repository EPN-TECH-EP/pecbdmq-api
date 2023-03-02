package epntech.cbdmq.pe.dominio.admin;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data

@Entity(name = "gen_unidad_gestion")
@Table(name = "gen_unidad_gestion")
@SQLDelete(sql = "UPDATE {h-schema}gen_unidad_gestion SET estado = 'ELIMINADO' WHERE cod_unidad_gestion = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class UnidadGestion {

	@Id
	@jakarta.persistence.GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_unidad_gestion")
	public int codigo;
	
	@Column(name = "unidad_gestion")
	public String nombre;
	
	@Column(name = "estado")
	private String estado;
}
