package epntech.cbdmq.pe.dominio.admin;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "gen_junta_formacion")
public class JuntaFormacion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_junta_formacion")
	private Long codJuntaFormacion;
	
	@Column(name = "cod_periodo_academico")
	private Long codPeriodoAcademico;
	
	/*@Column(name = "cod_documento")
	private Long codDocumento;*/
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cod_documento")
    private Documento documento;
	
}
