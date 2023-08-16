package epntech.cbdmq.pe.dominio.admin.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProfesionalizacionEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity(name = "pro_convocatoria_requisito")
@Table(name = "pro_convocatoria_requisito")
@SQLDelete(sql = "UPDATE {h-schema}pro_convocatoria_requisito SET estado = 'ELIMINADO' WHERE cod_convocatoria_requisitos = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ProConvocatoriaRequisito extends ProfesionalizacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    @Column(name = "cod_convocatoria_requisitos")
    private Integer codigo;
    @Column (name = "cod_requisito")
    private Integer codigoRequisito;
    @Column (name = "cod_convocatoria")
    private Integer codigoConvocatoria;

    @Column(name = "estado")
    private String estado;
}
