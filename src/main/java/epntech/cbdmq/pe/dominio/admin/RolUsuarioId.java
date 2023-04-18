package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class RolUsuarioId {
	
	protected Long codRol;
	protected Long codUsuario;
	
	public RolUsuarioId(Long codRol, Long codUsuario) {
		this.codRol = codRol;
		this.codUsuario = codUsuario;
	}
	
	public RolUsuarioId() {
		
	}

}
