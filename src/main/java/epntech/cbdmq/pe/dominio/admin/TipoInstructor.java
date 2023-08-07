package epntech.cbdmq.pe.dominio.admin;

import jakarta.validation.constraints.NotBlank;
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
@Entity(name = "gen_tipo_instructor")
@Table(name = "gen_tipo_instructor")
@SQLDelete(sql = "UPDATE {h-schema}gen_tipo_instructor SET estado = 'ELIMINADO' WHERE cod_tipo_instructor = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class TipoInstructor {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_tipo_instructor")
	private Integer codigo;

	@NotBlank(message = "El atributo 'nombre' es obligatorio")
	@Column(name = "nombre_tipo_instructor")	
	private String nombre;
	
	@Column(name = "estado")
	private String estado;
	
	
}
