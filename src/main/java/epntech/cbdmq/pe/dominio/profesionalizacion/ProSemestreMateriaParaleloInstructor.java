package epntech.cbdmq.pe.dominio.profesionalizacion;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity(name = "pro_periodo_semestre_materia_paralelo_instructor")
@Table(name = "pro_periodo_semestre_materia_paralelo_instructor")
@SQLDelete(sql = "UPDATE {h-schema}pro_periodo_semestre_materia_paralelo_instructor SET estado = 'ELIMINADO' WHERE cod_semestre_materia_paralelo_instructor = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ProSemestreMateriaParaleloInstructor extends ProfesionalizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    @Column(name = "cod_periodo_semestre_materia_paralelo_instructor")
    private Integer codPeriodoSemestreMateriaParaleloInstructor;

    @Column(name = "cod_periodo_semestre_materia_paralelo")
    private Integer codPeriodoSemestreMateriaParalelo;

    @Column(name = "cod_instructor")
    private Integer codInstructor;

    @Column(name = "estado")
    private String estado;
}
