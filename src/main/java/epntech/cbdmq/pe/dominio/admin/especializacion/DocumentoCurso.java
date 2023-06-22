package epntech.cbdmq.pe.dominio.admin.especializacion;


import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class DocumentoCurso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_documento")
	private Long codigoDocumento;
	
	@Column(name = "cod_tipo_documento")
	private Long codTipoDocumento;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "nombre_documento")
	private String nombreDocumento;
	
	@Column(name = "observaciones")
	private String observaciones;
	
	@Column(name = "ruta")
	private String ruta;
	
	@Column(name = "estado")
	private String estado;
}
