package epntech.cbdmq.pe.dominio.profesionalizacion;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity(name = "pro_periodo_semestre")
@Table(name ="pro_periodo_semestre")
@SQLDelete(sql = "UPDATE {h-schema}pro_periodo_semestre SET estado = 'ELIMINADO' WHERE cod_periodo_semestre = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ProPeriodoSemestre extends ProfesionalizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_periodo_semestre")
    private Integer codPeriodoSemestre;

    @Column(name = "cod_periodo")
    private Integer codPeriodo;

    @Column(name = "cod_semestre")
    private Integer codSemestre;

    @Column(name = "estado")
    private String estado;
}
