package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "gen_menu_rol")
@Data
public class MenuRol {

	@EmbeddedId
	protected MenuRolId menuRolId;
	protected String permisos;

	public MenuRol() {

	}

	public MenuRol(Long codMenu, Long codRol, String permisos) {
		this.menuRolId = new MenuRolId(codMenu, codRol);
		this.permisos = permisos;
	}

}
