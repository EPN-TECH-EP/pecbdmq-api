package epntech.cbdmq.pe.dominio.admin.especializacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import epntech.cbdmq.pe.dominio.util.InscripcionDatosEspecializacion;
import epntech.cbdmq.pe.dominio.util.InscripcionEstudianteDatosEspecializacion;
import epntech.cbdmq.pe.dominio.util.InscritosEspecializacion;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "esp_inscripcion")
@SQLDelete(sql = "UPDATE {h-schema}esp_inscripcion SET estado = 'ELIMINADO' WHERE cod_inscripcion = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")

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

@NamedNativeQuery(name = "InscripcionEsp.findInscripcionDatos", 
query = "select i.cod_inscripcion as codInscripcion, dp.cedula, dp.nombre, dp.apellido, dp.correo_personal as correoPersonal, cc.nombre_catalogo_curso as nombreCatalogoCurso, "
		+ "c.fecha_inicio_curso as fechaInicioCurso, c.fecha_fin_curso as fechaFinCurso, i.fecha_inscripcion as fechaInscripcion "
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
		resultSetMapping = "findInscripcionDatos")
@SqlResultSetMapping(name = "findInscripcionDatos", classes = @ConstructorResult(targetClass = InscripcionEstudianteDatosEspecializacion.class, columns = {
		@ColumnResult(name = "codInscripcion"), 
		@ColumnResult(name = "cedula"), 
		@ColumnResult(name = "nombre"),
		@ColumnResult(name = "apellido"),
		@ColumnResult(name = "correoPersonal"),
		@ColumnResult(name = "nombreCatalogoCurso"), 
		@ColumnResult(name = "fechaInicioCurso", type = LocalDate.class),
		@ColumnResult(name = "fechaFinCurso", type = LocalDate.class),
		@ColumnResult(name = "fechaInscripcion", type = LocalDate.class),}))

@NamedNativeQuery(name = "InscripcionEsp.findInscripcionPorCurso", 
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
		+ "and i.cod_curso_especializacion = :codCurso", 
		resultSetMapping = "findInscripcionPorCurso")
@SqlResultSetMapping(name = "findInscripcionPorCurso", classes = @ConstructorResult(targetClass = InscripcionDatosEspecializacion.class, columns = {
		@ColumnResult(name = "codInscripcion"), 
		@ColumnResult(name = "cedula"), 
		@ColumnResult(name = "nombre"),
		@ColumnResult(name = "apellido"), 
		@ColumnResult(name = "nombreCatalogoCurso"), }))

@NamedNativeQuery(name = "InscripcionEsp.findInscripcionValidaPorCurso", 
query = "select i.cod_inscripcion as codInscripcion, dp.cedula, dp.nombre, dp.apellido, cc.nombre_catalogo_curso as nombreCatalogoCurso, dp.correo_personal as correoPersonal "
		+ "from cbdmq.esp_inscripcion i, cbdmq.gen_estudiante e, cbdmq.gen_dato_personal dp, cbdmq.esp_curso c, cbdmq.esp_catalogo_cursos cc "
		+ "where i.cod_estudiante = e.cod_estudiante " 
		+ "and e.cod_datos_personales = dp.cod_datos_personales "
		+ "and i.cod_curso_especializacion = c.cod_curso_especializacion "
		+ "and c.cod_catalogo_cursos = cc.cod_catalogo_cursos " 
		+ "and upper(e.estado) = 'ACTIVO' "
		+ "and upper(dp.estado) = 'ACTIVO' " 
		+ "and upper(c.estado) = 'ACTIVO' "
		+ "and upper(cc.estado) = 'ACTIVO' "
		+ "and upper(i.estado) = 'VALIDO' "
		+ "and i.cod_curso_especializacion = :codCurso ", 
		resultSetMapping = "findInscripcionValidaPorCurso")
@SqlResultSetMapping(name = "findInscripcionValidaPorCurso", classes = @ConstructorResult(targetClass = InscritosEspecializacion.class, columns = {
		@ColumnResult(name = "codInscripcion"), 
		@ColumnResult(name = "cedula"), 
		@ColumnResult(name = "nombre"),
		@ColumnResult(name = "apellido"), 
		@ColumnResult(name = "nombreCatalogoCurso"), 
		@ColumnResult(name = "correoPersonal"),}))

public class InscripcionEsp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_inscripcion")
	private Long codInscripcion;

	@Column(name = "cod_estudiante")
	private Long codEstudiante;

	@Column(name = "cod_curso_especializacion")
	private Long codCursoEspecializacion;
	
	@Column(name = "fecha_inscripcion")
	private LocalDate fechaInscripcion;
	
	@Column(name = "estado")
	private String estado;
	
	@OneToMany(mappedBy = "codInscripcion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InscripcionDocumento> documentos = new ArrayList<>();

}

