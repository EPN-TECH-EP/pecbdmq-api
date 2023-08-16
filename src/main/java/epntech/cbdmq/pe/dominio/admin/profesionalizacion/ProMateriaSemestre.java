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
@Table(name = "pro_materia_semestre")
@SQLDelete(sql = "UPDATE {h-schema}pro_materia_semestre SET estado = 'ELIMINADO' WHERE cod_materia_semestre = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ProMateriaSemestre extends ProfesionalizacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    @Column(name = "cod_materia_semestre")
    private Integer codigo;

    @Column(name = "cod_materia")
    private Integer codMateria;

    @Column(name = "cod_periodo_semestre")
    private Integer codPeriodoSemestre;

    @Column(name = "cod_aula")
    private Integer codAula;

    @Column(name = "numero_horas")
    private Integer numeroHoras;

    @Column(name = "nota_minima")
    private Integer notaMinima;

    @Column(name = "nota_maxima")
    private Integer notaMaxima;

    @Column(name = "estado")
    private String estado;
}
