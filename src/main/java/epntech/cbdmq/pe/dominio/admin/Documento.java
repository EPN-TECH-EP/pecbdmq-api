package epntech.cbdmq.pe.dominio.admin;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_documento")
@SQLDelete(sql = "UPDATE {h-schema}gen_documento SET estado = 'ELIMINADO' WHERE cod_documento = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Documento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_documento")
	private Integer codigo;
	
	@Column(name = "area_doc")
	private String area;
	
	@Column(name = "autorizacion")
	private String autorizacion;
	
	@Column(name = "cod_datos_personales")
	private Integer codDatoPersonal;
	
	@Column(name = "cod_modulo")
	private Integer codModulo;
	
	@Column(name = "cod_tipo_documento")
	private Integer tipo;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "estado_validacion")
	private String estadoValidacion;
	
	@Column(name = "id_documento")
	private String idDocumento;
	
	@Column(name = "nombre_documento")
	private String nombre;
	
	@Column(name = "observaciones")
	private String observaciones;
	
	@Column(name = "ruta")
	private String ruta;
	
	@Column(name = "estado")
	private String estado;
}
