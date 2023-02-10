package epntech.cbdmq.pe.dominio;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "gen_usuario")
@Data
public class Usuario implements Serializable {

	private static final long serialVersionUID = 9203940391795653856L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_usuario_cod_usuario_seq")
	@Column(nullable = false, updatable = false)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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
