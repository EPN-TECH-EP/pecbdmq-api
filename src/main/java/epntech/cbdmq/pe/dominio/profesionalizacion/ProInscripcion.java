package epntech.cbdmq.pe.dominio.profesionalizacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import epntech.cbdmq.pe.dominio.admin.Documento;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "pro_inscripcion")
@Table(name = "pro_inscripcion")
@SQLDelete(sql = "UPDATE {h-schema}pro_inscripcion SET estado = 'ELIMINADO' WHERE cod_inscripcion = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ProInscripcion extends ProfesionalizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    @Column(name = "cod_inscripcion")
    private Integer codInscripcion;

    @Column(name = "cod_estudiante")
    private Integer codEstudiante;

    @Column(name = "cod_convocatoria")
    private Integer codConvocatoria;

    @Column(name = "adjunto")
    private String adjunto;

    @Column(name = "estado")
    private String estado;

    @Column(name = "fecha_inscripcion")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaInscripcion;

    @Column(name = "aceptado")
    private Boolean aceptado;

    @Column(name = "convalidacion")
    private Boolean convalidacion;

    @Column(name = "email")
    private String email;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "pro_inscripcion_documento",
            joinColumns = @JoinColumn(name = "cod_inscripcion"),
            inverseJoinColumns = @JoinColumn(name = "cod_documento")
    )
    private List<Documento> documentos = new ArrayList<>();


}
