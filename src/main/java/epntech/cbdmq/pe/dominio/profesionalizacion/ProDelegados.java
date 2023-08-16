package epntech.cbdmq.pe.dominio.profesionalizacion;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity(name = "pro_delegados")
@Table(name = "pro_delegados")
@SQLDelete(sql = "UPDATE {h-schema}pro_delegados SET estado = 'ELIMINADO' WHERE cod_delegado = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ProDelegados extends ProfesionalizacionEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    @Column(name = "cod_delegado")
    private Integer codDelegado;

    @Column(name = "cod_usuario")
    private Integer codUsuario;

    @Column(name = "estado")
    private String estado;
}
