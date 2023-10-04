package epntech.cbdmq.pe.dominio.admin;

import java.util.Date;

import epntech.cbdmq.pe.dominio.util.ApelacionEstudiante;
import jakarta.persistence.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
@NamedNativeQuery(name = "ApelacionEstudiante.getApelacionesEstudiantesMateria",
		query = "select\n" +
				"\tga.*,\n" +
				"\tm.nombre_materia ,\n" +
				"\tgdp.nombre || '  ' || gdp.apellido as \"nombre\",\n" +
				"\tge.codigo_unico_estudiante\n" +
				"\n" +
				"from\n" +
				"\tcbdmq.gen_apelacion ga\n" +
				"left join cbdmq.gen_nota_formacion gnf on\n" +
				"\tga.cod_nota_formacion = gnf.cod_nota_formacion\n" +
				"left join cbdmq.gen_estudiante_materia_paralelo emp on\n" +
				"\tgnf.cod_estudiante_materia_paralelo = emp.cod_estudiante_materia_paralelo\n" +
				"left join cbdmq.gen_estudiante ge on\n" +
				"\temp.cod_estudiante = ge.cod_estudiante\n" +
				"left join cbdmq.gen_dato_personal gdp on\n" +
				"\tge.cod_datos_personales = gdp.cod_datos_personales\n" +
				"left join cbdmq.gen_materia_paralelo mp on\n" +
				"\temp.cod_materia_paralelo = mp.cod_materia_paralelo\n" +
				"left join cbdmq.gen_paralelo p on\n" +
				"\tmp.cod_paralelo = p.cod_paralelo\n" +
				"left join cbdmq.gen_materia_periodo mpe on\n" +
				"\tmp.cod_materia_periodo = mpe.cod_materia_periodo\n" +
				"left join cbdmq.gen_materia m on\n" +
				"\tmpe.cod_materia = m.cod_materia\n" +
				"where\n" +
				"\tm.cod_materia = :codMateria\n" +
				"\tand ge.estado <> 'BAJA'\n" +
				"\tand mpe.cod_periodo_academico = :codPA\n" +
				"\tand ga.estado = 'ACTIVO'\n" +
				"\t",resultSetMapping = "ApelacionEstudiante"
)
@SqlResultSetMapping(name = "ApelacionEstudiante", classes =
@ConstructorResult(
		targetClass = ApelacionEstudiante.class,
		columns = {
				@ColumnResult(name = "cod_apelacion", type= Integer.class),
				@ColumnResult(name = "fecha_solicitud", type= Date.class),
				@ColumnResult(name = "observacion_estudiante", type= String.class),
				@ColumnResult(name = "observacion_instructor", type= String.class),
				@ColumnResult(name = "aprobacion", type= Boolean.class),
				@ColumnResult(name = "nota_actual", type= Double.class),
				@ColumnResult(name = "nota_nueva", type= Double.class),
				@ColumnResult(name = "estado", type= String.class),
				@ColumnResult(name = "cod_nota_formacion", type= Integer.class),
				@ColumnResult(name = "cod_nota_profesionalizacion", type= Integer.class),
				@ColumnResult(name = "nombre_materia", type= String.class),
				@ColumnResult(name = "nombre", type= String.class),
				@ColumnResult(name = "codigo_unico_estudiante", type= String.class),

		}))

@Data
@Entity
@Table(name = "gen_apelacion")
@SQLDelete(sql = "UPDATE {h-schema}gen_apelacion SET estado = 'ELIMINADO' WHERE cod_apelacion = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Apelacion {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_apelacion")
	private Integer codApelacion;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "fecha_solicitud")
	private Date fechaSolicitud;
	
	@Column(name = "observacion_estudiante")
	private String observacionEstudiante;
	
	@Column(name = "observacion_instructor")
	private String observacionInstructor;
	
	@Column(name = "aprobacion")
	private Boolean aprobacion;
	
	@Column(name = "nota_actual")
	private Double notaActual;
	
	@Column(name = "nota_nueva")
	private Double notaNueva;
	
	@Column(name = "estado")
   	private String estado;
	
	@Column(name = "cod_nota_formacion")
	private Integer codNotaFormacion;
	
	@Column(name = "cod_nota_profesionalizacion")
	private Integer codNotaProfesionalizacion;
	
}
