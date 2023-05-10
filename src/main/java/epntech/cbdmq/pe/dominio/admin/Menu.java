package epntech.cbdmq.pe.dominio.admin;

import java.io.Serializable;

import org.hibernate.annotations.NamedNativeQuery;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "gen_menu")
@Data

/*@NamedNativeQuery(name = "MenuPermisos.findMenuByIdUsuario", query = "select "
		//+ "	m.id, m.etiqueta, coalesce (m.ruta, '-') as ruta, coalesce (m.menu_padre,0) as menu_padre, coalesce (m.orden,0) as orden, 	coalesce (mr.permisos, '-') as permisos "
		+ "	m.cod_menu, m.etiqueta, m.ruta, m.menu_padre, m.orden, m.descripcion, m.icono, mr.permisos "
		+ " from "
		+ "	cbdmq.gen_menu_rol mr, "
		+ "	cbdmq.gen_menu m, "
		+ "	(select mp.cod_menu, mp.orden, coalesce(mp.menu_padre, 1 / coalesce(mp.orden, 999) * 100000 + mp.cod_menu * 10000) as factor from cbdmq.gen_menu mp where menu_padre is null) padre "
		+ " where (m.cod_menu = padre.cod_menu or m.menu_padre = padre.cod_menu) and "
		+ "	mr.cod_rol in ( "
		+ "	select "
		+ "		cod_rol "
		+ "	from "
		+ "		cbdmq.gen_rol_usuario ru "
		+ "	where "
		+ "		cod_usuario = (select u.cod_usuario  from cbdmq.gen_usuario u where u.nombre_usuario = :id_usuario) "
		+ "	) "
		+ " and  "
		+ "	mr.cod_menu  = m.cod_menu  "
		+ " order by "
		+ "	padre.factor + coalesce(m.orden, 0)  + (case when m.menu_padre is null then 1000 else 0 end) desc",
		resultSetMapping = "MenuPermisos"
		)*/

@NamedNativeQuery(name = "MenuPermisos.findMenuByIdUsuario", query = "	select m.cod_menu, "
		+ "	m.etiqueta, "
		+ "	m.ruta, "
		+ "	m.menu_padre, "
		+ "	m.orden, "
		+ "	m.descripcion, "
		+ "	m.icono, "
		+ "	permisos "
		+ "	from cbdmq.gen_menu m,	 "
		+ "	(select distinct cod_menu, permisos from cbdmq.gen_menu_rol gmr where cod_rol in "
		+ "	(select cod_rol	from cbdmq.gen_rol_usuario ru where cod_usuario =  "
		+ "	(select u.cod_usuario from cbdmq.gen_usuario u where u.nombre_usuario = :id_usuario))) permisos "
		+ "	where m.cod_menu = permisos.cod_menu "
		+ "	order by coalesce(menu_padre, 0), orden",
		resultSetMapping = "MenuPermisos")

@SqlResultSetMapping(name = "MenuPermisos", classes = @ConstructorResult(targetClass = MenuPermisos.class, columns = {
		@ColumnResult(name = "cod_menu"),
		@ColumnResult(name = "etiqueta"),
		@ColumnResult(name = "ruta"),
		@ColumnResult(name = "menu_padre"),
		@ColumnResult(name = "orden"),
		@ColumnResult(name = "descripcion"),
		@ColumnResult(name = "icono"),
		@ColumnResult(name = "permisos")		
}))
public class Menu implements Serializable {
	
	private static final long serialVersionUID = 2695780129293062043L;

	//@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	protected Integer codMenu;

	protected String etiqueta;
	protected String ruta;
	@Column(name = "menu_padre")
	protected Integer menuPadre;
	protected Integer orden;
	protected String icono;
	protected String descripcion;

	public Menu() {
	}

	public Menu(Integer codMenu, String etiqueta, String ruta, Integer menu_padre, Integer orden, String descripcion, String icono) {
		super();
		this.codMenu = codMenu;
		this.etiqueta = etiqueta;
		this.ruta = ruta;
		this.menuPadre = menu_padre;
		this.orden = orden;
		this.icono = icono;
		this.descripcion = descripcion;
	}

}
