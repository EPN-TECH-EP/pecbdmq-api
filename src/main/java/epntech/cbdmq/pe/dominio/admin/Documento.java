package epntech.cbdmq.pe.dominio.admin;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gen_documento")

public class Documento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_documento")
	private Integer codigo;
	
	@Column(name = "cod_tipo_documento")
	private Integer tipo;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	
    @Column(name = "nombre_documento")
	private String nombre;
	
	@Column(name = "observaciones")
	private String observaciones;
	
	@Column(name = "ruta")
	private String ruta= "temp/";
	
	@Column(name = "estado")
	private String estado;

}
