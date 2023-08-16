package epntech.cbdmq.pe.dominio.profesionalizacion;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity(name = "pro_periodo_estudiante_semestre_materia")
@Table(name = "pro_periodo_estudiante_semestre_materia")
@SQLDelete(sql = "UPDATE {h-schema}pro_periodo_estudiante_semestre_materia SET estado = 'ELIMINADO' WHERE cod_pro_estudiante_semestre_materia = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ProEstudianteSemestreMateria extends ProfesionalizacionEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    @Column(name = "cod_periodo_estudiante_semestre_materia")
    private Integer codPeriodoEstudianteSemestreMateria;

    @Column(name = "cod_periodo")
    private Integer codPeriodo;

    @Column(name = "cod_periodo_estudiante_semestre")
    private Integer codPeriodoEstudianteSemestre;

    @Column(name = "cod_materia")
    private Integer codMateria;

    @Column(name = "estado")
    private String estado;
}
