package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.*;

import lombok.*;

@Data

@Entity(name = "gen_requisito")
@Table(name = "gen_requisito")
public class Requisito {

	@Id
	@GeneratedValue(strategy  = GenerationType.IDENTITY)
	@Column(name = "cod_requisito")
	private Integer codigo;
	
	@Column(name = "cod_convocatoria")
	private Integer codConvocatoria;
	
	@Column(name = "cod_funcionario")
	private Integer codFuncionario;
	
	@Column(name = "cod_documento_habilitante")
	private Integer codDocumentoHabilitante;
	
	@Column(name = "nombre_requisito")
	private String nombre;
	
	@Column(name = "descripcion_requisito")
	private String descripcion;
	
	@Column(name = "is_active")
	private Boolean estado;
}
