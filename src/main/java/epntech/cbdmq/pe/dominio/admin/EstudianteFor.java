package epntech.cbdmq.pe.dominio.admin;

import epntech.cbdmq.pe.dominio.admin.formacion.NotaMateriaByEstudiante;
import epntech.cbdmq.pe.dominio.fichaPersonal.formacion.DatosEstudianteParaCrearUsuario;
import jakarta.persistence.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Data;

@SqlResultSetMapping(name = "DatosEstudianteParaCrearUsuario", classes =
@ConstructorResult(
		targetClass = DatosEstudianteParaCrearUsuario.class,
		columns = {
				@ColumnResult(name = "cod_datos_personales", type= Long.class),
				@ColumnResult(name = "cedula", type= String.class)
		}))
@NamedNativeQuery(name = "cbdmq.listar_estudiantes_formacion_generar_usuarios",
		query = "select * from cbdmq.listar_estudiantes_formacion_generar_usuarios()",resultSetMapping = "DatosEstudianteParaCrearUsuario"
)

@Data
@Entity
@Table(name = "gen_estudiante")
@SQLDelete(sql = "UPDATE {h-schema}gen_estudiante SET estado = 'ELIMINADO' WHERE cod_estudiante = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class EstudianteFor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_estudiante")
	private Integer codEstudiante;
	
	@Column(name = "cod_datos_personales")
	private Integer codDatosPersonales;
	
	@Column(name = "cod_modulo")
	private Integer codModulo;
	
	@Column(name = "cod_unico_estudiante")
	private String codUnicoEstudiante;
	
	@Column(name = "estado")
	private String estado;
	
	
}
