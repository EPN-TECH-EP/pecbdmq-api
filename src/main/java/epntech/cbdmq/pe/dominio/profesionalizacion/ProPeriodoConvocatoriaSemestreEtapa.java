package epntech.cbdmq.pe.dominio.profesionalizacion;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity(name = "pro_periodo_convocatoria_semestre_etapa")
@Table(name ="pro_periodo_convocatoria_semestre_etapa")
@SQLDelete(sql = "UPDATE {h-schema}pro_periodo_convocatoria_semestre_etapa SET estado = 'ELIMINADO' WHERE cod_periodo_convocatoria_semestre_etapa = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ProPeriodoConvocatoriaSemestreEtapa extends ProfesionalizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_periodo_convocatoria_semestre_etapa")
    private Integer codPeriodoConvocatoriaSemestreEtapa;

    @Column(name = "cod_periodo")
    private Integer codPeriodo;

    @Column(name = "cod_convocatoria")
    private Integer codConvocatoria;

    @Column(name = "cod_semestre")
    private Integer codSemestre;

    @Column(name = "cod_etapa")
    private Integer codEtapa;

    @Column(name = "estado")
    private String estado;
}
