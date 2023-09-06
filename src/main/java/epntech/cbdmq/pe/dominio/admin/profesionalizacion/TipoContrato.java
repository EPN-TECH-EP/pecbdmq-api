package epntech.cbdmq.pe.dominio.admin.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProfesionalizacionEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity
@Table(name = "gen_tipo_contrato")
@SQLDelete(sql = "UPDATE {h-schema}gen_tipo_contrato SET estado = 'ELIMINADO' WHERE cod_tipo_contrato = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class TipoContrato extends ProfesionalizacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    @Column(name = "cod_tipo_contrato")
    private Integer codigo;

    @Column(name = "nombre_tipo_contrato")
    private String nombre;

    @Column(name = "estado")
    private String estado;
}
