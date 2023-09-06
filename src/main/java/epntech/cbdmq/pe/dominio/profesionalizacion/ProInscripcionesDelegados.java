package epntech.cbdmq.pe.dominio.profesionalizacion;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity(name = "pro_inscripciones_delegados")
@Table(name = "pro_inscripciones_delegados")
@SQLDelete(sql = "UPDATE {h-schema}pro_inscripciones_delegados SET estado = 'ELIMINADO' WHERE cod_inscripciones_delegados = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ProInscripcionesDelegados extends ProfesionalizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    @Column(name = "cod_inscripciones_delegados")
    private Integer codInscripcionesDelegados;

    @Column(name = "cod_inscripciones")
    private Integer codInscripciones;

    @Column(name = "cod_delegados")
    private Integer codDelegados;

    @Column(name = "estado")
    private String estado;

}
