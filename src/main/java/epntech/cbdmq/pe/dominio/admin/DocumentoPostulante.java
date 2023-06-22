package epntech.cbdmq.pe.dominio.admin;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gen_documento")
@SQLDelete(sql = "UPDATE {h-schema}gen_documento SET estado = 'ELIMINADO' WHERE cod_documento = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class DocumentoPostulante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_documento")
	private Integer codDocumento;
	
	@Column(name = "autorizacion")
	private String autorizacion;
	
	@Column(name = "cod_tipo_documento")
	private Integer codTipoDocumento;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "estado_validacion")
	private String estadoValidacion;	
	
	@Column(name = "nombre_documento")
	private String nombreDocumento;
	
	@Column(name = "observaciones")
	private String observaciones;
	
	@Column(name = "ruta")
	private String ruta;
	
	@Column(name = "estado")
	private String estado;

}
