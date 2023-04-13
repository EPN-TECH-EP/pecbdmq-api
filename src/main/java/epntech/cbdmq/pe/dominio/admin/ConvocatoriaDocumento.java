
package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity(name = "gen_documento")
@Table(name = "gen_documento")

public class ConvocatoriaDocumento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_documento")
	private Integer cod_documento;
	@Column(name = "cod_tipo_documento")
	private Integer cod_tipo_documento;
	@Column(name = "nombre_documento")
	private String nombre_documento;
	@Column(name = "codigo_unico_documento")
	private String id_documento;
	@Column(name = "descripcion")
	private String descripcion;
	@Column(name = "observaciones")
	private String observaciones;
	@Column(name = "ruta")
	private String ruta;
	@Column(name = "estado_validacion")
	private String estado_validacion;
	@Column(name = "autorizacion")
	private String autorizacion;
	@Column(name = "estado")
	private String estado;

	
}



