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
@Table(name = "gen_documento_habilitante")
@SQLDelete(sql = "UPDATE {h-schema}gen_documento_habilitante SET estado = 'ELIMINADO' WHERE cod_documento_habilitante = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class DocumentoHabilitante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_documento_habilitante")
	private Integer codDocumentoHabilitante;
	
	@Column(name = "lista_documentos")
	private String nombre;
	
	@Column(name = "estado")
	private String estado;
}
