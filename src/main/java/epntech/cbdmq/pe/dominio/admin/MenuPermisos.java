package epntech.cbdmq.pe.dominio.admin;

import lombok.Data;

@Data
public class MenuPermisos extends Menu {

	private static final long serialVersionUID = -7349833346217199491L;

	private String permisos;

	public MenuPermisos(Integer codMenu, String etiqueta, String ruta, Integer menu_padre, Integer orden, String descripcion, String icono, String permisos) {
		super();

		this.codMenu = codMenu;
		this.etiqueta = etiqueta;
		this.ruta = ruta;
		this.menuPadre = menu_padre;
		this.orden = orden;
		this.permisos = permisos;
		this.descripcion = descripcion;
		this.icono = icono;		
	}

}
