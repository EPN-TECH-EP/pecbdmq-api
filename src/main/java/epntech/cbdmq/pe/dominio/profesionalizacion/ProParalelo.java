package epntech.cbdmq.pe.dominio.profesionalizacion;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity
@Table(name = "pro_paralelo")
@SQLDelete(sql = "UPDATE {h-schema}pro_paralelo SET estado = 'ELIMINADO' WHERE cod_paralelo = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ProParalelo extends ProfesionalizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    @Column(name = "cod_paralelo")
    private Integer codParalelo;

    @Column(name = "nombre_paralelo")
    private String nombreParalelo;

    @Column(name = "estado")
    private String estado;
}
