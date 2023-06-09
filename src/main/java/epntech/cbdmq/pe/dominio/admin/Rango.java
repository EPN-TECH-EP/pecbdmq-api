package epntech.cbdmq.pe.dominio.admin;

import epntech.cbdmq.pe.dominio.util.RangoDtoRead;
import epntech.cbdmq.pe.dominio.util.UsuarioDtoRead;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity
@Table(name = "gen_rango")
@SQLDelete(sql = "UPDATE {h-schema}gen_rango SET estado = 'ELIMINADO' WHERE cod_rango = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")


@NamedNativeQuery(name = "RangoDtoRead.listarPersonalizado",
        query = "select r1_0.cod_rango,r1_0.nombre_rango,r1_0.estado from {h-schema}gen_rango r1_0 where r1_0.cod_grado=:codGrado",
        resultSetMapping = "RangoDtoRead"
)
@SqlResultSetMapping(name = "RangoDtoRead", classes = @ConstructorResult(targetClass = RangoDtoRead.class, columns = {
        @ColumnResult(name = "cod_rango"),
        @ColumnResult(name = "nombre_rango"),
        @ColumnResult(name = "estado")
}))

public class Rango {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_rango")
    private Integer codRango;
    @Column(name = "nombre_rango")
    private String nombre;
    @Column(name = "estado")
    private String estado;


    //Mapeado con un rango
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cod_grado")
    private Grado codGrado;


}
