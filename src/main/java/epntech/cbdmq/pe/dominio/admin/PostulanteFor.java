package epntech.cbdmq.pe.dominio.admin;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonFormat;

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
@Entity
@Table(name = "gen_postulante")
@SQLDelete(sql = "UPDATE {h-schema}gen_postulante SET estado = 'ELIMINADO' WHERE cod_postulante = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class PostulanteFor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_postulante")
	private Integer codPostulante;
	
	@Column(name = "cod_datos_personales")
	private Integer codDatoPersonal;
	
	@Column(name = "id_postulante")
	private String idPostulante;
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "fecha_postulacion")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fechaPostulacion;
	
	@Column(name = "edad_postulacion")
	private Integer edadPostulacion;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "gen_postulante_documento", joinColumns = @JoinColumn(name = "cod_postulante"), inverseJoinColumns = @JoinColumn(name = "cod_documento"))
	private Set<DocumentoPostulante> documentos = new HashSet<>();
}
