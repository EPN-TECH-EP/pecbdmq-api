package epntech.cbdmq.pe.dominio.admin.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProfesionalizacionEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity(name = "pro_tipo_proyecto")
@Table(name = "pro_tipo_proyecto")
@SQLDelete(sql = "UPDATE {h-schema}pro_tipo_proyecto SET estado = 'ELIMINADO' WHERE cod_tipo_proyecto = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ProTipoProyecto extends ProfesionalizacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    @Column(name = "cod_tipo_proyecto")
    private Integer codigo;
    @Column (name = "nombre_tipo")
    private String nombreTipo;

    @Column(name = "estado")
    private String estado;
}
