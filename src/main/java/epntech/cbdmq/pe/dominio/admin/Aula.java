package epntech.cbdmq.pe.dominio.admin;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import lombok.*;


@Data
@Entity
@Table(name = "gen_aula")
@SQLDelete(sql = "UPDATE {h-schema}gen_aula SET estado = 'ELIMINADO' WHERE cod_aula = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Aula {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include()
	@Column(name = "cod_aula")
	private Integer codAula;
	
	@Column(name = "nombre_aula")
	private String nombreAula;
	
	@Column(name = "capacidad")
	private Integer capacidad;
	
	@Column(name = "tipo_aula")
	private String tipoAula;
	
	@Column(name = "numero_pcs")
	private Integer numeroPcs;
	
	@Column(name = "numero_impresoras")
	private Integer numeroImpresoras;
	
	@Column(name = "tipo_internet")
	private String tipoInternet;	
	
	@Column(name = "numero_proyectores")
	private Integer numeroProyectores;
		
	@Column(name = "pc_instructor")
	private String pcInstructor;
	
	@Column(name = "sala_ocupada")
	private Boolean salaOcupada;
	
	@Column(name = "estado")
	private String estado;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "gen_materia_aula",
            joinColumns = @JoinColumn(name = "cod_aula"),
            inverseJoinColumns = @JoinColumn(name = "cod_materia")
    )
	private List<Materia> materias = new ArrayList<>();

    
		
}
