package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "gen_rol_usuario")
@Data
public class RolUsuario {

	@EmbeddedId
	protected RolUsuarioId rolUsuarioId;

	public RolUsuario(RolUsuarioId rolUsuarioId) {
		this.rolUsuarioId = rolUsuarioId;
	}

	public RolUsuario(Long codRol, Long codUsuario) {
		this.rolUsuarioId = new RolUsuarioId(codRol, codUsuario);
	}

	public RolUsuario() {
	}

}
