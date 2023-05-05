package epntech.cbdmq.pe.dominio;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.dominio.util.EstudianteDatos;
import epntech.cbdmq.pe.dominio.util.UsuarioDtoRead;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.NamedNativeQuery;

@Entity
@Table(name = "gen_usuario")
@Data
@NamedNativeQuery(name = "UsuarioDtoRead.buscarUsuarioPersonalizado",
		query = "select u.cod_usuario, u.cod_modulo, u.nombre_usuario, u.clave, u.fecha_ultimo_login, u.fecha_ultimo_login_mostrar,u.fecha_registro, u.is_active, u.is_not_locked, dp.nombre, dp.apellido, dp.correo_personal from {h-schema}gen_usuario u join {h-schema}gen_dato_personal dp on dp.cod_datos_personales = u.cod_datos_personales",
		resultSetMapping = "UsuarioDtoRead"
)
@SqlResultSetMapping(name = "UsuarioDtoRead", classes = @ConstructorResult(targetClass = UsuarioDtoRead.class, columns = {
		@ColumnResult(name = "cod_usuario"),
		@ColumnResult(name = "cod_modulo"),
		@ColumnResult(name = "nombre_usuario"),
		@ColumnResult(name = "clave"),
		@ColumnResult(name = "fecha_ultimo_login"),
		@ColumnResult(name = "fecha_ultimo_login_mostrar"),
		@ColumnResult(name = "fecha_registro"),
		@ColumnResult(name = "is_active"),
		@ColumnResult(name = "is_not_locked"),
		@ColumnResult(name = "nombre"),
		@ColumnResult(name = "apellido"),
		@ColumnResult(name = "correo_personal")
}))
public class Usuario implements Serializable {

	private static final long serialVersionUID = 9203940391795653856L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_usuario_cod_usuario_seq")
	@Column(nullable = false, updatable = false)
	//@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Long codUsuario;

	private Long codModulo;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cod_datos_personales")
	private DatoPersonal codDatosPersonales;

	// private String nombres;
	// private String apellidos;
	private String nombreUsuario;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String clave;
	// private String email;
	// private String urlImagenPerfil;
	private Date fechaUltimoLogin;
	private Date fechaUltimoLoginMostrar;
	private Date fechaRegistro;
	private boolean isActive;
	private boolean isNotLocked;

	public Usuario(Long codUsuario, Long codModulo, DatoPersonal codDatosPersonales, String nombreUsuario, String clave,
			Date fechaUltimoLogin, Date fechaUltimoLoginMostrar, Date fechaRegistro, boolean isActive,
			boolean isNotLocked) {
		super();
		this.codUsuario = codUsuario;
		this.codModulo = codModulo;
		this.codDatosPersonales = codDatosPersonales;
		this.nombreUsuario = nombreUsuario;
		this.clave = clave;
		this.fechaUltimoLogin = fechaUltimoLogin;
		this.fechaUltimoLoginMostrar = fechaUltimoLoginMostrar;
		this.fechaRegistro = fechaRegistro;
		this.isActive = isActive;
		this.isNotLocked = isNotLocked;
	}

	public Usuario() {
		// TODO Auto-generated constructor stub
	}

}
