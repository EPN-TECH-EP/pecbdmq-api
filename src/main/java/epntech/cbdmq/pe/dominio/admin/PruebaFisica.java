package epntech.cbdmq.pe.dominio.admin;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;


@Data

@Entity(name = "gen_parametriza_pru_fisica")
@Table(name = "gen_parametriza_pru_fisica")
@SQLDelete(sql = "UPDATE {h-schema}gen_parametriza_pru_fisica SET estado = 'ELIMINADO' WHERE cod_parametriza_fisica = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class PruebaFisica {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_parametriza_fisica")
	private Integer cod_parametriza_fisica;
	@Column(name = "edad_inicio")
	private Integer edadinicio;
	@Column(name = "edad_fin")
	private Integer edadfin;
	@Column(name = "sexo")
	private String sexo;
	@Column(name = "calificacion")
	private Integer calificacion;
	@Column(name = "peso_prueba")
	private Integer pesoprueba;
	@Column(name = "valor")
	private Integer valor;
	@Column(name = "no_min_flexion")
	private Integer nominflexion;
	@Column(name = "estado")
	private String estado;
	
	
	
	
	
}
