package epntech.cbdmq.pe.dominio.profesionalizacion;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity(name = "pro_periodo_semestre_materia_paralelo")
@Table(name = "pro_periodo_semestre_materia_paralelo")
@SQLDelete(sql = "UPDATE {h-schema}pro_periodo_semestre_materia_paralelo SET estado = 'ELIMINADO' WHERE cod_semestre_materia_paralelo = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ProSemestreMateriaParalelo extends ProfesionalizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    @Column(name = "cod_semestre_materia_paralelo")
    private Integer codSemestreMateriaParalelo;

    @Column(name = "cod_semestre_materia")
    private Integer codSemestreMateria;

    @Column(name = "cod_paralelo")
    private Integer codParalelo;


    @Column(name = "estado")
    private String estado;

    @Column(name = "cod_proyecto")
    private Integer codProyecto;

}
