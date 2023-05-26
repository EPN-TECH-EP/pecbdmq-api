package epntech.cbdmq.pe.dominio.admin;

import java.time.LocalDateTime;
import java.util.Set;

import epntech.cbdmq.pe.dominio.util.DatoPersonalDto;
import jakarta.persistence.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;

import epntech.cbdmq.pe.dominio.Usuario;
import lombok.Data;

@Entity(name = "gen_dato_personal")
@Table(name = "gen_dato_personal")
@Data
@SQLDelete(sql = "UPDATE {h-schema}gen_dato_personal SET estado = 'ELIMINADO' WHERE cod_datos_personales = ?", check = ResultCheckStyle.COUNT)

@NamedNativeQuery(name = "DatoPersonalDto.informacionDetallada",
		query = "select gdp.cedula,gdp.nombre,gdp.apellido," +
				"case when ge.cod_datos_personales is not null then 1 else 0 end as \"Estudiante\"\n" +
				",case when gf.cod_datos_personales is not null then 1 else 0 end as \"Funcionario\"\n" +
				",case when gi.cod_datos_personales is not null then 1 else 0 end as \"Instructor\"" +
				",gdp.fecha_nacimiento,gdp.tipo_sangre,gdp.tipo_nacionalidad,gdp.correo_personal,gdp.correo_institucional,gpn.nombre as \"provincia_nacimiento\",gcn.nombre as \"canton_nacimiento\",gpr.nombre as \"provincia_residencia\",gcr.nombre as \"canton_residencia\",gdp.num_telef_convencional,gdp.num_telef_celular,gdp.calle_principal_residencia,gdp.calle_secundaria_residencia,gdp.numero_casa,gdp.colegio,gdp.nombre_titulo,gdp.pais_titulo,gdp.ciudad_titulo,gdp.tiene_merito_deportivo,gdp.tiene_merito_academico,gdp.merito_deportivo_descripcion,gdp.merito_academico_descripcion,cg.nombre_cargo as Cargo,rg.nombre_rango as Rango,gd.nombre_grado as Grado\n" +
				"from cbdmq.gen_estudiante ge\n" +
				"left join cbdmq.gen_dato_personal gdp on ge.cod_datos_personales =gdp.cod_datos_personales \n" +
				"left join cbdmq.gen_provincia gpn on gpn.cod_provincia =gdp.cod_provincia_nacimiento \n" +
				"left join cbdmq.gen_provincia gpr on gpr.cod_provincia =gdp.cod_provincia_residencia  \n" +
				"left join cbdmq.gen_canton gcn on gcn.cod_canton=gdp.cod_canton_nacimiento\n" +
				"left join cbdmq.gen_canton gcr on gcr.cod_canton=gdp.cod_canton_residencia\n" +
				"left join cbdmq.gen_cargo cg on cg.cod_cargo=gdp.cod_cargo \n" +
				"left join cbdmq.gen_rango rg on rg.cod_rango=gdp.cod_rango \n" +
				"left join cbdmq.gen_grado gd on gd.cod_grado=gdp.cod_grado \n" +
				"left join cbdmq.gen_funcionario gf on gf.cod_datos_personales =gdp.cod_datos_personales \n" +
				"left join cbdmq.gen_instructor gi on gi.cod_datos_personales =gdp.cod_datos_personales\n" +
				"where gdp.cedula =:cedula",
		resultSetMapping = "DatoPersonalDto"
)
@SqlResultSetMapping(name = "DatoPersonalDto", classes = @ConstructorResult(targetClass = DatoPersonalDto.class, columns = {
		@ColumnResult(name = "cedula"),
		@ColumnResult(name = "nombre"),
		@ColumnResult(name = "apellido"),
		@ColumnResult(name = "Estudiante"),
		@ColumnResult(name = "Funcionario"),
		@ColumnResult(name = "Instructor"),
		@ColumnResult(name = "fecha_nacimiento"),
		@ColumnResult(name = "tipo_sangre"),
		@ColumnResult(name = "tipo_nacionalidad"),
		@ColumnResult(name = "correo_personal"),
		@ColumnResult(name = "correo_institucional"),
		@ColumnResult(name = "provincia_nacimiento"),
		@ColumnResult(name = "canton_nacimiento"),
		@ColumnResult(name = "provincia_residencia"),
		@ColumnResult(name = "canton_residencia"),
		@ColumnResult(name = "num_telef_convencional"),
		@ColumnResult(name = "num_telef_celular"),
		@ColumnResult(name = "calle_principal_residencia"),
		@ColumnResult(name = "calle_secundaria_residencia"),
		@ColumnResult(name = "numero_casa"),
		@ColumnResult(name = "colegio"),
		@ColumnResult(name = "nombre_titulo"),
		@ColumnResult(name = "pais_titulo"),
		@ColumnResult(name = "ciudad_titulo"),
		@ColumnResult(name = "tiene_merito_deportivo"),
		@ColumnResult(name = "tiene_merito_academico"),
		@ColumnResult(name = "merito_deportivo_descripcion"),
		@ColumnResult(name = "merito_academico_descripcion"),
		@ColumnResult(name = "cargo"),
		@ColumnResult(name = "rango"),
		@ColumnResult(name = "grado"),

}))
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
	//@Column(name = "genero")
	//private String genero;
	@Column(name = "sexo")
	private String sexo;
	@Column(name = "num_telef_celular")
	private String num_telef_celular;
	//@Column(name = "canton_nacimiento")
	//private String canton_nacimiento;
	@Column(name = "reside_pais")
	private Boolean reside_pais;
	@Column(name = "cod_provincia_residencia")
	private Long cod_provincia_residencia;
	//@Column(name = "canton_residencia")
	//private String canton_residencia;
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
/*	@Column(name = "nombre_titulo")
	private String nombre_titulo;
	@Column(name = "pais_titulo")
	private String pais_titulo;
	@Column(name = "ciudad_titulo")
	private String ciudad_titulo;*/

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
/*	@Column(name = "titulo_tercer_nivel")
	private String titulo_tercer_nivel;
	@Column(name = "cod_canton_tercer_nivel")
	private Long cod_canton_tercer_nivel;
	@Column(name = "cod_provincia_tercer_nivel")
	private Long cod_provincia_tercer_nivel;*/

	@Column(name = "cod_canton_tercernivel")
	private Integer cod_canton_tercer_nivel;
	@Column(name = "cod_provincia_tercernivel")
	private Integer cod_provincia_tercer_nivel;
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
	@Column(name = "cod_provincia_cuartonivel")
	private Long cod_provincia_cuartonivel;
	@Column(name = "cod_canton_cuartonivel")
	private Long cod_canton_cuartonivel;
}