package epntech.cbdmq.pe.dominio.admin.especializacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import epntech.cbdmq.pe.dominio.util.DatosInscripcionEsp;
import epntech.cbdmq.pe.dto.especializacion.NotasEspecializacionDTO;
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
        query = "select i.cod_inscripcion as codInscripcion, dp.cedula, dp.nombre, dp.apellido, "
                + "cc.nombre_catalogo_curso as nombreCatalogoCurso, i.cod_usuario as codUsuario, "
                + "datosUsuario.nombre_usuario as nombreUsuario, datosUsuario.correo_personal as correoUsuario "
                + "from cbdmq.esp_inscripcion i, cbdmq.gen_estudiante e, cbdmq.gen_dato_personal dp, "
                + "cbdmq.esp_curso c, cbdmq.esp_catalogo_cursos cc, "
                + "(select gu.cod_usuario, apellido || ' ' || nombre as nombre_usuario, correo_personal "
                + "from cbdmq.gen_dato_personal gdp2, "
                + "cbdmq.gen_usuario gu	"
                + "where gdp2.cod_datos_personales = gu.cod_datos_personales"
                + ") as datosUsuario "
                + "where i.cod_estudiante = e.cod_estudiante "
                + "and e.cod_datos_personales = dp.cod_datos_personales "
                + "and i.cod_curso_especializacion = c.cod_curso_especializacion "
                + "and c.cod_catalogo_cursos = cc.cod_catalogo_cursos "
                + "and upper(e.estado) = 'ACTIVO' "
                + "and upper(dp.estado) = 'ACTIVO' "
                + "and upper(c.estado) <> 'ELIMINADO' "
                + "and upper(cc.estado) = 'ACTIVO'"
                + "and upper(i.estado) IN('ABIERTO', 'PENDIENTE', 'ASIGNADO') "
                + "and datosUsuario.cod_usuario = i.cod_usuario",
        resultSetMapping = "findInscripciones")
@SqlResultSetMapping(name = "findInscripciones", classes = @ConstructorResult(targetClass = InscripcionDatosEspecializacion.class, columns = {
        @ColumnResult(name = "codInscripcion"),
        @ColumnResult(name = "cedula"),
        @ColumnResult(name = "nombre"),
        @ColumnResult(name = "apellido"),
        @ColumnResult(name = "nombreCatalogoCurso"),
        @ColumnResult(name = "codUsuario"),
        @ColumnResult(name = "nombreUsuario"),
        @ColumnResult(name = "correoUsuario"),}))

@NamedNativeQuery(name = "InscripcionEsp.findInscripcionesByUsuario",

        query = "select i.cod_inscripcion as codInscripcion, dp.cedula, dp.nombre, dp.apellido, cc.nombre_catalogo_curso as nombreCatalogoCurso, i.estado as estado "
                + "from cbdmq.esp_inscripcion i, cbdmq.gen_estudiante e, cbdmq.gen_dato_personal dp, cbdmq.esp_curso c, cbdmq.esp_catalogo_cursos cc "
                + "where i.cod_estudiante = e.cod_estudiante "
                + "and e.cod_datos_personales = dp.cod_datos_personales "
                + "and i.cod_curso_especializacion = c.cod_curso_especializacion "
                + "and c.cod_catalogo_cursos = cc.cod_catalogo_cursos "
                + "and upper(e.estado) = 'ACTIVO' "
                + "and upper(dp.estado) = 'ACTIVO' "
                + "and upper(c.estado) <> 'ELIMINADO' "
                + "and upper(cc.estado) = 'ACTIVO' "
                + "and upper(i.estado) IN('ABIERTO', 'PENDIENTE', 'ASIGNADO') "
                + "and i.cod_usuario = :codUsuario "
                + "union all "
                + "select i.cod_inscripcion as codInscripcion, dp.cedula, dp.nombre, dp.apellido, cc.nombre_catalogo_curso as nombreCatalogoCurso, i.estado as estado "
                + "from cbdmq.esp_inscripcion i, cbdmq.gen_estudiante e, cbdmq.gen_dato_personal dp, cbdmq.esp_curso c, cbdmq.esp_catalogo_cursos cc "
                + "where i.cod_estudiante = e.cod_estudiante "
                + "and e.cod_datos_personales = dp.cod_datos_personales "
                + "and i.cod_curso_especializacion = c.cod_curso_especializacion "
                + "and c.cod_catalogo_cursos = cc.cod_catalogo_cursos "
                + "and upper(e.estado) = 'ACTIVO' "
                + "and upper(dp.estado) = 'ACTIVO' "
                + "and upper(c.estado) <> 'ELIMINADO' "
                + "and upper(cc.estado) = 'ACTIVO' "
                + "and upper(i.estado) IN('ABIERTO', 'PENDIENTE', 'ASIGNADO')",
        resultSetMapping = "findInscripcionesByUsuario")

@SqlResultSetMapping(name = "findInscripcionesByUsuario", classes = @ConstructorResult(targetClass = InscripcionDatosEspecializacion.class, columns = {
        @ColumnResult(name = "codInscripcion"),
        @ColumnResult(name = "cedula"),
        @ColumnResult(name = "nombre"),
        @ColumnResult(name = "apellido"),
        @ColumnResult(name = "nombreCatalogoCurso"),
        @ColumnResult(name = "estado"),}))

@NamedNativeQuery(name = "InscripcionEsp.findInscripcion",
        query = "select i.cod_inscripcion as codInscripcion, dp.cedula, dp.nombre, dp.apellido, cc.nombre_catalogo_curso as nombreCatalogoCurso "
                + "from cbdmq.esp_inscripcion i, cbdmq.gen_estudiante e, cbdmq.gen_dato_personal dp, cbdmq.esp_curso c, cbdmq.esp_catalogo_cursos cc "
                + "where i.cod_estudiante = e.cod_estudiante "
                + "and e.cod_datos_personales = dp.cod_datos_personales "
                + "and i.cod_curso_especializacion = c.cod_curso_especializacion "
                + "and c.cod_catalogo_cursos = cc.cod_catalogo_cursos "
                + "and upper(e.estado) = 'ACTIVO' "
                + "and upper(dp.estado) = 'ACTIVO' "
                + "and upper(c.estado) <> 'ELIMINADO' "
                + "and upper(cc.estado) = 'ACTIVO' "
                + "and i.cod_inscripcion = :codInscripcion",
        resultSetMapping = "findInscripcion")
@SqlResultSetMapping(name = "findInscripcion", classes = @ConstructorResult(targetClass = InscripcionDatosEspecializacion.class, columns = {
        @ColumnResult(name = "codInscripcion"),
        @ColumnResult(name = "cedula"),
        @ColumnResult(name = "nombre"),
        @ColumnResult(name = "apellido"),
        @ColumnResult(name = "nombreCatalogoCurso"),}))

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
                + "and upper(c.estado) <> 'ELIMINADO' "
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
                + "and upper(c.estado) <> 'ELIMINADO' "
                + "and upper(cc.estado) = 'ACTIVO' "
                + "and i.cod_curso_especializacion = :codCurso",
        resultSetMapping = "findInscripcionPorCurso")
@SqlResultSetMapping(name = "findInscripcionPorCurso", classes = @ConstructorResult(targetClass = InscripcionDatosEspecializacion.class, columns = {
        @ColumnResult(name = "codInscripcion"),
        @ColumnResult(name = "cedula"),
        @ColumnResult(name = "nombre"),
        @ColumnResult(name = "apellido"),
        @ColumnResult(name = "nombreCatalogoCurso"),}))

@NamedNativeQuery(name = "InscripcionEsp.findInscripcionValidaPorCurso",
        query = "select i.cod_inscripcion as codInscripcion, dp.cedula, dp.nombre, dp.apellido, cc.nombre_catalogo_curso as nombreCatalogoCurso, dp.correo_personal as correoPersonal, e.codigo_unico_estudiante as codigoUnicoEstudiante "
                + "from cbdmq.esp_inscripcion i, cbdmq.gen_estudiante e, cbdmq.gen_dato_personal dp, cbdmq.esp_curso c, cbdmq.esp_catalogo_cursos cc "
                + "where i.cod_estudiante = e.cod_estudiante "
                + "and e.cod_datos_personales = dp.cod_datos_personales "
                + "and i.cod_curso_especializacion = c.cod_curso_especializacion "
                + "and c.cod_catalogo_cursos = cc.cod_catalogo_cursos "
                + "and upper(e.estado) = 'ACTIVO' "
                + "and upper(dp.estado) = 'ACTIVO' "
                + "and upper(c.estado) <> 'ELIMINADO' "
                + "and upper(cc.estado) = 'ACTIVO' "
                + "and upper(i.estado) <> 'ELIMINADO' and upper(i.estado) <> 'PENDIENTE' "
                + "and i.cod_curso_especializacion = :codCurso ",
        resultSetMapping = "findInscripcionValidaPorCurso")
@SqlResultSetMapping(name = "findInscripcionValidaPorCurso", classes = @ConstructorResult(targetClass = InscritosEspecializacion.class, columns = {
        @ColumnResult(name = "codInscripcion"),
        @ColumnResult(name = "cedula"),
        @ColumnResult(name = "nombre"),
        @ColumnResult(name = "apellido"),
        @ColumnResult(name = "nombreCatalogoCurso"),
        @ColumnResult(name = "correoPersonal"),
        @ColumnResult(name = "codigoUnicoEstudiante")
}
)
)


@NamedNativeQuery(name = "InscripcionEsp.getListasByEstado",
        query = "SELECT \n" +
                "    ei.cod_inscripcion, \n" +
                "    gdp.cedula, \n" +
                "    gdp.nombre, \n" +
                "    gdp.apellido, \n" +
                "    ec.descripcion as catalogoCurso, \n" +
                "    ei.estado, \n" +
                "    gu.nombre_usuario, \n" +
                "    gdp.correo_personal , \n" +
                "    gu.cod_usuario \n" +
                "FROM cbdmq.esp_inscripcion ei \n" +
                "LEFT JOIN cbdmq.gen_estudiante ge ON ge.cod_estudiante = ei.cod_estudiante \n" +
                "LEFT JOIN cbdmq.gen_dato_personal gdp ON gdp.cod_datos_personales = ge.cod_datos_personales \n" +
                "LEFT JOIN cbdmq.gen_usuario gu ON gu.cod_datos_personales = gdp.cod_datos_personales\n" +
                "left join cbdmq.esp_curso ec on ec.cod_curso_especializacion = ei.cod_curso_especializacion \n" +
                "where ei.estado =:estado\n" +
                "and ec.cod_curso_especializacion = :codCurso",
        resultSetMapping = "findInscripcionPorEstadoCurso")
@SqlResultSetMapping(name = "findInscripcionPorEstadoCurso", classes = @ConstructorResult(targetClass = InscripcionDatosEspecializacion.class, columns = {
        @ColumnResult(name = "cod_inscripcion", type = Long.class),
        @ColumnResult(name = "cedula"),
        @ColumnResult(name = "nombre"),
        @ColumnResult(name = "apellido"),
        @ColumnResult(name = "catalogoCurso"),
        @ColumnResult(name = "estado"),
        @ColumnResult(name = "nombre_usuario"),
        @ColumnResult(name = "correo_personal"),
        @ColumnResult(name = "cod_usuario", type = Long.class),

}))
@NamedNativeQuery(name = "DatosInscripcionEsp.aprobadosPruebas",
        query = "select * from cbdmq.get_estudiantes_aprobados_pruebas_curso(:codCurso)",
        resultSetMapping = "aprobadosPruebas")
@SqlResultSetMapping(name = "aprobadosPruebas", classes = @ConstructorResult(targetClass = DatosInscripcionEsp.class, columns = {
        @ColumnResult(name = "cod_estudiante", type = Integer.class),
        @ColumnResult(name = "codigo_unico_estudiante", type = String.class),
        @ColumnResult(name = "cedula", type = String.class),
        @ColumnResult(name = "nombre", type = String.class),
        @ColumnResult(name = "apellido", type = String.class),
        @ColumnResult(name = "correo_personal", type = String.class),
        @ColumnResult(name = "correo_institucional", type = String.class),}))
@NamedNativeQuery(name = "DatosInscripcionEsp.aprobadosPruebasBySubtipoPrueba",
        query = "select * from cbdmq.get_estudiantes_aprobados_pruebas_curso(:codCurso,:codSubtipoPrueba)",
        resultSetMapping = "aprobadosPruebasSubTipoPrueba")
@SqlResultSetMapping(name = "aprobadosPruebasSubTipoPrueba", classes = @ConstructorResult(targetClass = DatosInscripcionEsp.class, columns = {
        @ColumnResult(name = "cod_estudiante", type = Integer.class),
        @ColumnResult(name = "codigo_unico_estudiante", type = String.class),
        @ColumnResult(name = "cedula", type = String.class),
        @ColumnResult(name = "nombre", type = String.class),
        @ColumnResult(name = "apellido", type = String.class),
        @ColumnResult(name = "correo_personal", type = String.class),
        @ColumnResult(name = "correo_institucional", type = String.class),}))

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

    @Column(name = "cod_usuario")
    private Long codUsuario;


    @OneToMany(mappedBy = "codInscripcion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InscripcionDocumento> documentos = new ArrayList<>();

}

