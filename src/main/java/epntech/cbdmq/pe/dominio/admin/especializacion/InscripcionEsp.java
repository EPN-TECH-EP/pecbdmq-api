package epntech.cbdmq.pe.dominio.admin.especializacion;

import java.time.LocalDate;

import org.hibernate.annotations.NamedNativeQuery;

import epntech.cbdmq.pe.dominio.util.InscripcionDatosEspecializacion;
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
@Table(name = "esp_inscripcion")

@NamedNativeQuery(name = "InscripcionEsp.findInscripciones", 
query = "select i.cod_inscripcion as codInscripcion, dp.cedula, dp.nombre, dp.apellido, cc.nombre_catalogo_curso as nombreCatalogoCurso "
		+ "from cbdmq.esp_inscripcion i, cbdmq.gen_estudiante e, cbdmq.gen_dato_personal dp, cbdmq.esp_curso c, cbdmq.esp_catalogo_cursos cc "
		+ "where i.cod_estudiante = e.cod_estudiante " 
		+ "and e.cod_datos_personales = dp.cod_datos_personales "
		+ "and i.cod_curso_especializacion = c.cod_curso_especializacion "
		+ "and c.cod_catalogo_cursos = cc.cod_catalogo_cursos " 
		+ "and upper(e.estado) = 'ACTIVO' "
		+ "and upper(dp.estado) = 'ACTIVO' " 
		+ "and upper(c.estado) = 'ACTIVO' "
		+ "and upper(cc.estado) = 'ACTIVO'", 
		resultSetMapping = "findInscripciones")
@SqlResultSetMapping(name = "findInscripciones", classes = @ConstructorResult(targetClass = InscripcionDatosEspecializacion.class, columns = {
		@ColumnResult(name = "codInscripcion"), 
		@ColumnResult(name = "cedula"), 
		@ColumnResult(name = "nombre"),
		@ColumnResult(name = "apellido"), 
		@ColumnResult(name = "nombreCatalogoCurso"), }))

@NamedNativeQuery(name = "InscripcionEsp.findInscripcion", 
query = "select i.cod_inscripcion as codInscripcion, dp.cedula, dp.nombre, dp.apellido, cc.nombre_catalogo_curso as nombreCatalogoCurso "
		+ "from cbdmq.esp_inscripcion i, cbdmq.gen_estudiante e, cbdmq.gen_dato_personal dp, cbdmq.esp_curso c, cbdmq.esp_catalogo_cursos cc "
		+ "where i.cod_estudiante = e.cod_estudiante " 
		+ "and e.cod_datos_personales = dp.cod_datos_personales "
		+ "and i.cod_curso_especializacion = c.cod_curso_especializacion "
		+ "and c.cod_catalogo_cursos = cc.cod_catalogo_cursos " 
		+ "and upper(e.estado) = 'ACTIVO' "
		+ "and upper(dp.estado) = 'ACTIVO' " 
		+ "and upper(c.estado) = 'ACTIVO' "
		+ "and upper(cc.estado) = 'ACTIVO' "
		+ "and i.cod_inscripcion = :codInscripcion", 
		resultSetMapping = "findInscripcion")
@SqlResultSetMapping(name = "findInscripcion", classes = @ConstructorResult(targetClass = InscripcionDatosEspecializacion.class, columns = {
		@ColumnResult(name = "codInscripcion"), 
		@ColumnResult(name = "cedula"), 
		@ColumnResult(name = "nombre"),
		@ColumnResult(name = "apellido"), 
		@ColumnResult(name = "nombreCatalogoCurso"), }))

public class InscripcionEsp {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_inscripcion")
	private Long codInscripcion;
	
	@Column(name = "cod_estudiante")
	private Long codEstudiante;
	
	@Column(name = "cod_curso_especializacion")
	private Long codCursoEspecializacion;

}
