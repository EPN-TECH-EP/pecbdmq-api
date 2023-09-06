package epntech.cbdmq.pe.dominio.profesionalizacion;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity(name = "pro_periodo_semestre_materia_paralelo_estudiante")
@Table(name = "pro_periodo_semestre_materia_paralelo_estudiante")
@SQLDelete(sql = "UPDATE {h-schema}pro_periodo_semestre_materia_paralelo_estudiante SET estado = 'ELIMINADO' WHERE cod_paralelo_estudiante = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ProSemestreMateriaParaleloEstudiante extends ProfesionalizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    @Column(name = "cod_paralelo_estudiante")
    private Integer codParaleloEstudiante;

    @Column(name = "cod_estudiante")
    private Integer codEstudiante;

    @Column(name = "cod_semestre_materia_paralelo")
    private Integer codSemestreMateriaParalelo;

    @Column(name = "estado")
    private String estado;
}
