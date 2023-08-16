package epntech.cbdmq.pe.dominio.profesionalizacion;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity(name = "pro_etapas")
@Table(name = "pro_etapas")
@SQLDelete(sql = "UPDATE {h-schema}pro_etapas SET estado = 'ELIMINADO' WHERE cod_etapa = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ProEtapas extends ProfesionalizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    @Column(name = "cod_etapa")
    private Integer codEtapa;

    @Column(name = "nombre_etapa")
    private String nombreEtapa;

    @Column(name = "estado")
    private String estado;
}
