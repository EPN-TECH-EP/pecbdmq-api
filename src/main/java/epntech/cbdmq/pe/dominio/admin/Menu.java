package epntech.cbdmq.pe.dominio.admin;

import java.io.Serializable;

import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "gen_menu")
@Data

@NamedNativeQuery(name = "MenuPermisos.findMenuByIdUsuario", query = "select "
		//+ "	m.id, m.etiqueta, coalesce (m.ruta, '-') as ruta, coalesce (m.menu_padre,0) as menu_padre, coalesce (m.orden,0) as orden, 	coalesce (mr.permisos, '-') as permisos "
		+ "	m.cod_menu, m.etiqueta, m.ruta, m.menu_padre, m.orden, mr.permisos "
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
		)	

@SqlResultSetMapping(name = "MenuPermisos", classes = @ConstructorResult(targetClass = MenuPermisos.class, columns = {
		@ColumnResult(name = "cod_menu"),
		@ColumnResult(name = "etiqueta"),
		@ColumnResult(name = "ruta"),
		@ColumnResult(name = "menu_padre"),
		@ColumnResult(name = "orden"),
		@ColumnResult(name = "permisos")		
}))

public class Menu implements Serializable {
	
	private static final long serialVersionUID = 2695780129293062043L;

	//@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	protected Integer id;

	protected String etiqueta;
	protected String ruta;
	protected Integer menu_padre;
	protected Integer orden;

	public Menu() {
	}

	public Menu(Integer id, String etiqueta, String ruta, Integer menu_padre, Integer orden) {
		super();
		this.id = id;
		this.etiqueta = etiqueta;
		this.ruta = ruta;
		this.menu_padre = menu_padre;
		this.orden = orden;
	}

}
