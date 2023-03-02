package epntech.cbdmq.pe.dominio.admin;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;

import lombok.*;

@Data
@Entity
@Table(name = "gen_estacion_trabajo")
@SQLDelete(sql = "UPDATE {h-schema}gen_estacion_trabajo SET estado = 'ELIMINADO' WHERE cod_estacion = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class EstacionTrabajo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include()
	@Column(name = "cod_estacion")
	private Integer codigo;
	
	@Column(name = "nombre_zona")
	private String nombre;
	
	@Column(name = "canton")
	private String canton;
    
		
}
