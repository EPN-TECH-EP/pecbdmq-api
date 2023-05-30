package epntech.cbdmq.pe.dominio.admin;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;

import epntech.cbdmq.pe.dominio.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity(name = "gen_dato_personal")
@Table(name = "gen_dato_personal")
@Data
@SQLDelete(sql = "UPDATE {h-schema}gen_dato_personal SET estado = 'ELIMINADO' WHERE cod_datos_personales = ?", check = ResultCheckStyle.COUNT)
//@Where(clause = "estado <> 'ELIMINADO'")
public class DatoPersonal {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_dato_personal_cod_datos_personales_seq")
	@SequenceGenerator(name = "gen_dato_personal_cod_datos_personales_seq", sequenceName = "gen_dato_personal_cod_datos_personales_seq", allocationSize = 1)
	@Column(name = "cod_datos_personales")
	private Integer cod_datos_personales;
	@Column(name = "apellido")
	private String apellido;
	@Column(name = "cedula")
	private String cedula;
	@Column(name = "cod_estacion")
	private Integer cod_estacion;
	@Column(name = "correo_personal")
	private String correo_personal;
	@Column(name = "estado")
	private String estado;
	@Column(name = "fecha_nacimiento")
	private LocalDateTime fecha_nacimiento;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "num_telef_convencional")
	private String num_telef_convencional;
	@Column(name = "tipo_sangre")
	private String tipo_sangre;
	@Column(name = "validacion_correo")
	private String validacion_correo;
	@Column(name = "cod_provincia_nacimiento")
	private Integer cod_provincia_nacimiento;
	@Column(name = "cod_unidad_gestion")
	private Integer cod_unidad_gestion;
	@Column(name = "sexo")
	private String sexo;
	@Column(name = "num_telef_celular")
	private String num_telef_celular;
	@Column(name = "reside_pais")
	private Boolean reside_pais;
	@Column(name = "cod_provincia_residencia")
	private Long cod_provincia_residencia;
	@Column(name = "calle_principal_residencia")
	private String calle_principal_residencia;
	@Column(name = "calle_secundaria_residencia")
	private String calle_secundaria_residencia;
	@Column(name = "numero_casa")
	private String numero_casa;
	@Column(name = "colegio")
	private String colegio;
	@Column(name = "tipo_nacionalidad")
	private String tipo_nacionalidad;
	@Column(name = "tiene_merito_deportivo")
	private Boolean tiene_merito_deportivo;
	@Column(name = "tiene_merito_academico")
	private Boolean tiene_merito_academico;

	@Column(name = "nombre_titulo_segundonivel")
	private String nombre_titulo_segundonivel;
	@Column(name = "pais_titulo_segundonivel")
	private String pais_titulo_segundonivel;
	@Column(name = "ciudad_titulo_segundonivel")
	private String ciudad_titulo_segundonivel;
	@Column(name = "merito_deportivo_descripcion")
	private String merito_deportivo_descripcion;
	@Column(name = "merito_academico_descripcion")
	private String merito_academico_descripcion;
	@Column(name = "pin_validacion_correo")
	private String pin_validacion_correo;
	@Column(name = "correo_institucional")
	private String correo_institucional;
	@Column(name = "cod_cargo")
	private Long cod_cargo;
	@Column(name = "cod_rango")
	private Long cod_rango;
	@Column(name = "cod_grado")
	private Long cod_grado;
	@Column(name = "cod_documento_imagen")
	private Integer cod_documento_imagen;
	@Column(name = "cod_canton_nacimiento")
	private Long cod_canton_nacimiento;
	@Column(name = "cod_canton_residencia")
	private Long cod_canton_residencia;

	@Column(name = "fecha_salida_institucion")
	private LocalDateTime fecha_salida_institucion;
	@Column(name = "nivel_instruccion")
	private String nivel_instruccion;
	@Column(name = "nombre_titulo_tercernivel")
	private String nombre_titulo_tercernivel;
	@Column(name = "nombre_titulo_cuartonivel")
	private String nombre_titulo_cuartonivel;
	@Column(name = "es_vulnerable")
	private Boolean es_vulnerable;
	@Column(name = "pais_titulo_cuartonivel")
	private String pais_titulo_cuartonivel;
	@Column(name = "pais_titulo_tercernivel")
	private String pais_titulo_tercernivel;
}

