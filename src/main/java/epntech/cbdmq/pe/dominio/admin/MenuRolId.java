package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class MenuRolId {
	protected Integer codMenu;
	protected Integer codRol;
	
	public MenuRolId(Integer codMenu2, Integer codRol2) {
		this.codMenu = codMenu2;
		this.codRol = codRol2;
	}
	
	public MenuRolId() {
		
	}

}
