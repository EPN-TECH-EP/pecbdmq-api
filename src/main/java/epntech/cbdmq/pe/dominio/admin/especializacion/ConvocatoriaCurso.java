package epntech.cbdmq.pe.dominio.admin.especializacion;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonFormat;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.util.InscripcionDatosEspecializacion;
import epntech.cbdmq.pe.dominio.util.ListaRequisitos;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "gen_convocatoria")
@SQLDelete(sql = "UPDATE {h-schema}gen_convocatoria SET estado = 'ELIMINADO' WHERE cod_convocatoria = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")

@NamedNativeQuery(name = "ConvocatoriaCurso.findRequisitos",
        query = "select r.nombre_requisito as nombreRequisito\r\n"
                + "from cbdmq.esp_curso_requisito cr, cbdmq.gen_requisito r, cbdmq.gen_convocatoria c\r\n"
                + "where cr.cod_requisito = r.cod_requisito\r\n"
                + "and cr.cod_curso_especializacion = c.cod_curso_especializacion \r\n"
                + "and upper(c.estado) = 'ACTIVO' \r\n"
                + "and c.cod_convocatoria = :codConvocatoria",
        resultSetMapping = "findRequisitos")
@SqlResultSetMapping(name = "findRequisitos", classes = @ConstructorResult(targetClass = ListaRequisitos.class, columns = {
        @ColumnResult(name = "nombreRequisito"),}))

public class ConvocatoriaCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    @Column(name = "cod_convocatoria")
    private Long codConvocatoria;

    @Column(name = "nombre_convocatoria")
    private String nombreConvocatoria;

    @Column(name = "estado")
    private String estado;

    @Column(name = "fecha_inicio_convocatoria")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicioConvocatoria;

    @Column(name = "fecha_fin_convocatoria")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFinConvocatoria;

    @Column(name = "hora_inicio_convocatoria")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaInicioConvocatoria;

    @Column(name = "hora_fin_convocatoria")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaFinConvocatoria;

    @Column(name = "codigo_unico_convocatoria")
    private String codigoUnicoConvocatoria;
    @Column(name = "correo")
    private String correo;

    @Column(name = "cod_curso_especializacion")
    private Long codCursoEspecializacion;

    @Column(name = "fecha_actual")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaActual;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "gen_convocatoria_documento",
            joinColumns = @JoinColumn(name = "cod_convocatoria"),
            inverseJoinColumns = @JoinColumn(name = "cod_documento")
    )
    private List<Documento> documentos = new ArrayList<>();
}
