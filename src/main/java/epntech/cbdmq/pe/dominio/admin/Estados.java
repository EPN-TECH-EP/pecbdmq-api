
package epntech.cbdmq.pe.dominio.admin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import lombok.*;


@Data
@Entity(name = "gen_catalogo_estados")
@Table(name = "gen_catalogo_estados")
@SQLDelete(sql = "UPDATE {h-schema}gen_catalogo_estados SET estado = 'ELIMINADO' WHERE cod_catalogo_estados = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Estados {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_catalogo_estados")
	private Integer codigo;
	
	@Column(name = "nombre_catalogo_estados")
	private String nombre;
	
	@Column(name = "estado")
	private String estado;
	
}

