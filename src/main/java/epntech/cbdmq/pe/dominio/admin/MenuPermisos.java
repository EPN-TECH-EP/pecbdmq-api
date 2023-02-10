package epntech.cbdmq.pe.dominio.admin;

import lombok.Data;

@Data
public class MenuPermisos extends Menu {

	private static final long serialVersionUID = -7349833346217199491L;

	private String permisos;

	public MenuPermisos(Integer id, String etiqueta, String ruta, Integer menu_padre, Integer orden, String permisos) {
		super();

		this.id = id;
		this.etiqueta = etiqueta;
		this.ruta = ruta;
		this.menu_padre = menu_padre;
		this.orden = orden;
		this.permisos = permisos;
	}

}
