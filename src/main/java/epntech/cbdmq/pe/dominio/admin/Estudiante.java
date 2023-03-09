package epntech.cbdmq.pe.dominio.admin;

import java.io.Serializable;

import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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

@Data
@Entity
@Table(name = "gen_estudiante")
@SQLDelete(sql = "UPDATE {h-schema}gen_estudiante SET estado = 'ELIMINADO' WHERE cod_estudiante = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")

@NamedNativeQuery(name = "EstudianteDatos.findAllEstudiante", 
				query = "select e.cod_estudiante, e.grado, e.resultado_estudiante, e.id_estudiante, "
					+ "d.nombre, d.apellido, d.cedula, d.ciudad, d.correo_personal, d.fecha_nacimiento, d.num_telef, d.tipo_sangre, "
					+ "(select s.nombre_zona from {h-schema}gen_estacion_trabajo s where d.cod_estacion = s.cod_estacion and UPPER(s.estado) != 'ELIMINADO') as estacion, "
					+ "(select p.nombre from {h-schema}gen_provincia p where d.cod_provincia = p.cod_provincia and UPPER(p.estado) != 'ELIMINADO') as provincia, "
					+ "(select u.unidad_gestion from {h-schema}gen_unidad_gestion u where d.cod_unidad_gestion = u.cod_unidad_gestion and UPPER(u.estado) != 'ELIMINADO') as unidad_gestion, "
					+ "e.estado "
					+ "from {h-schema}gen_estudiante e, {h-schema}gen_dato_personal d, {h-schema}gen_modulo m "
					+ "where e.cod_datos_personales = d.cod_datos_personales "
					+ "and e.cod_modulo = m.cod_modulo "
					+ "and UPPER(e.estado) != 'ELIMINADO' "
					+ "and UPPER(d.estado) != 'ELIMINADO' "
					+ "and UPPER(m.estado) != 'ELIMINADO' ",
					resultSetMapping = "EstudianteDatos"
					)	
					
					@SqlResultSetMapping(name = "EstudianteDatos", classes = @ConstructorResult(targetClass = EstudianteDatos.class, columns = {
					@ColumnResult(name = "cod_estudiante"),
					@ColumnResult(name = "grado"),
					@ColumnResult(name = "resultado_estudiante"),
					@ColumnResult(name = "id_estudiante"),
					@ColumnResult(name = "nombre"),
					@ColumnResult(name = "apellido"),
					@ColumnResult(name = "cedula"),
					@ColumnResult(name = "ciudad"),
					@ColumnResult(name = "correo_personal"),
					@ColumnResult(name = "fecha_nacimiento"),
					@ColumnResult(name = "num_telef"),
					@ColumnResult(name = "tipo_sangre"),
					@ColumnResult(name = "estacion"),
					@ColumnResult(name = "provincia"),
					@ColumnResult(name = "unidad_gestion"),
					@ColumnResult(name = "estado")	
					}))

public class Estudiante implements Serializable  {

	

	private static final long serialVersionUID = 1935713547872014437L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer codEstudiante;
	
	protected Integer codDatosPersonales;
	
	protected Integer codModulo;
	
	protected String grado;
	
	protected String resultadoEstudiante;
	
	protected String idEstudiante;
	
	protected String estado;
	
	public Estudiante() {
	}
	
	public Estudiante(Integer codEstudiante, Integer codDatosPersonales, Integer codModulo, String grado,
			String resultadoEstudiante, String idEstudiante, String estado) {
		super();
		this.codEstudiante = codEstudiante;
		this.codDatosPersonales = codDatosPersonales;
		this.codModulo = codModulo;
		this.grado = grado;
		this.resultadoEstudiante = resultadoEstudiante;
		this.idEstudiante = idEstudiante;
		this.estado = estado;
	}
	
}
