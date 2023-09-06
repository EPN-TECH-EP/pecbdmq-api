package epntech.cbdmq.pe.dominio.profesionalizacion;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity(name = "pro_instructor")
@Table(name = "pro_instructor")
@SQLDelete(sql = "UPDATE {h-schema}pro_instructor SET estado = 'ELIMINADO' WHERE cod_instructor = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ProInstructor extends ProfesionalizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    @Column(name = "cod_instructor")
    private Integer codInstructor;

    @Column(name = "cod_datos_personales")
    private Integer codDatosPersonales;

    @Column(name = "cod_tipo_procedencia")
    private Integer codTipoProcedencia;

    @Column(name = "cod_estacion")
    private Integer codEstacion;

    @Column(name = "cod_unidad_gestion")
    private Integer codUnidadGestion;

    @Column(name = "cod_tipo_contrato")
    private Integer codTipoContrato;

    @Column(name = "estado")
    private String estado;

}
