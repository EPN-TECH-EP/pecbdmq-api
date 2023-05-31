package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="gen_baja_documento")
public class BajaDocumento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_baja_documento")
	private Integer codBajaDocumento;
	//@Column(name = "cod_documento")
	//private Integer codDocumento;
	@Column(name = "cod_baja")
	private Integer codBaja;

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cod_documento", referencedColumnName = "cod_documento")
    private Documento documento;
}
