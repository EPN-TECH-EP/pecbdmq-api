package epntech.cbdmq.pe.dominio.profesionalizacion;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity(name = "pro_cumple_requisitos")
@Table(name = "pro_cumple_requisitos")
@SQLDelete(sql = "UPDATE {h-schema}pro_cumple_requisitos SET estado = 'ELIMINADO' WHERE cod_cumple_requisito = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ProCumpleRequisitos extends ProfesionalizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    @Column(name = "cod_cumple_requisito")
    private Integer codCumpleRequisito;

    @Column(name = "cod_inscripcion")
    private Integer codInscripcion;

    @Column(name = "cod_requisito")
    private Integer codRequisito;

    @Column(name = "estado")
    private String estado;

    @Column(name = "cumple")
    private Boolean cumple;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "observacion_muestra")
    private String observacionMuestra;
}
