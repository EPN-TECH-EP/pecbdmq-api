package epntech.cbdmq.pe.dominio.admin;


import epntech.cbdmq.pe.dominio.util.DelegadoPK;
import jakarta.persistence.*;

import lombok.*;

@Data
@Entity
@Table(name = "gen_delegado")
@IdClass(DelegadoPK.class)
public class Delegado {

	@Id
	@Column(name = "cod_usuario")
	private Integer codUsuario;
	
	@Id
	@Column(name = "cod_periodo_academico")
	private Integer codPeriodoAcademico;

	@Column(name = "estado")
	private String estado;
		
}
