package epntech.cbdmq.pe.dominio.admin.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProfesionalizacionEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity
@Table(name = "pro_estudiante_semestre")
@SQLDelete(sql = "UPDATE {h-schema}pro_estudiante_semestre SET estado = 'ELIMINADO' WHERE cod_estudiante_semestre = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ProEstudianteSemestre extends ProfesionalizacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    @Column(name = "cod_estudiante_semestre")
    private Integer codigo;
    @Column (name = "cod_estudiante")
    private Integer codigoEstudiante;
    @Column(name = "cod_semestre")
    private Integer codigoSemestre;
    @Column (name = "cod_periodo")
    private Integer codigoPeriodo;

    @Column(name = "estado")
    private String estado;

    /*@ManyToMany(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinTable(name = "gen_estudiante", joinColumns = @JoinColumn(name = "cod_estudiante"), inverseJoinColumns = @JoinColumn(name = "cod_estudiante"))
    private Set<Estudiante> estudiante;*/

}
