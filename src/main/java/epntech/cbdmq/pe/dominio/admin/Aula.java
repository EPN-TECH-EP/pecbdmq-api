package epntech.cbdmq.pe.dominio.admin;

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
	private Integer codigo;
	
	@Column(name = "nombre_aula")
	private String nombre;
	
	@Column(name = "capacidad")
	private Integer capacidad;
	
	@Column(name = "tipo_aula")
	private String tipo;
	
	@Column(name = "numero_pcs")
	private Integer pcs;
	
	@Column(name = "numero_impresoras")
	private Integer impresoras;
	
	@Column(name = "tipo_internet")
	private String internet;	
	
	@Column(name = "numero_proyectores")
	private Integer proyectores;
		
	@Column(name = "pc_instructor")
	private String instructor;
	
	@Column(name = "sala_ocupada")
	private String salaOcupada;
	
	@Column(name = "estado")
	private String estado;
	
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "gen_materia_aula",
            joinColumns = @JoinColumn(name = "cod_aula"),
            inverseJoinColumns = @JoinColumn(name = "cod_materia")
    )
	private List<Materia> materias = new ArrayList<>();

    
		
}
