package epntech.cbdmq.pe.dominio.admin;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DelegadoPK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8636112012760142904L;

	@Column(name = "cod_usuario")
    private Integer codUsuario;

    @Column(name = "cod_periodo_academico")
    private Integer codPeriodoAcademico;

}
