package epntech.cbdmq.pe.dominio.admin;


import java.util.Set;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity
@Table(name = "gen_canton")
@SQLDelete(sql = "UPDATE {h-schema}gen_canton SET estado = 'ELIMINADO' WHERE cod_canton = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Canton {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include()
	@Column(name = "cod_canton")
	private Integer codigo;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "estado")
	private String estado;

	@Column(name = "cod_provincia")
	private Integer codProvincia;
    
	@OneToMany(mappedBy="codCanton")
    private Set<Parroquia> parroquias;
		
}
