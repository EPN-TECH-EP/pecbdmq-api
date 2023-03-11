package epntech.cbdmq.pe.dominio.admin;

import java.util.Set;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;

import lombok.*;

@Data
@Entity
@Table(name = "gen_provincia")
@SQLDelete(sql = "UPDATE {h-schema}gen_provincia SET estado = 'ELIMINADO' WHERE cod_provincia = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Provincia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include()
	@Column(name = "cod_provincia")
	private Integer codigo;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "estado")
	private String estado;

    
	@OneToMany(mappedBy="codProvincia")
    private Set<Canton> cantones;
}
