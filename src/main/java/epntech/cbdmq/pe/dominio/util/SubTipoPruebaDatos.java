package epntech.cbdmq.pe.dominio.util;

import epntech.cbdmq.pe.dominio.admin.SubTipoPrueba;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
//@Entity(name = "SubTipoPruebaDatos")
//@Entity
public class SubTipoPruebaDatos {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "cod_subtipo_prueba")
	private Integer codSubtipoPrueba;	

//	@Column(name = "cod_tipo_prueba")
	private Integer codTipoPrueba;	

//	@Column(name = "nombre")
	private String nombre;

//	@Column(name = "estado")
	private String estado;
	private String tipoPrueba;
	private Boolean esFisica;

	public SubTipoPruebaDatos(){}

	public SubTipoPruebaDatos(Integer codSubtipoPrueba, Integer codTipoPrueba, String nombre, String estado, String tipoPrueba, Boolean esFisica) {
		this.codSubtipoPrueba = codSubtipoPrueba;
		this.codTipoPrueba = codTipoPrueba;
		this.nombre = nombre;
		this.estado = estado;
		this.tipoPrueba = tipoPrueba;
		this.esFisica = esFisica;
	}
}
