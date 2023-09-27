package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity(name = "gen_reporte_parametro")
@Table(name = "gen_reporte_parametro")
@SQLDelete(sql = "UPDATE {h-schema}gen_reporte_parametro SET estado = 'ELIMINADO' WHERE codigo = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ReporteParametro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Long codigo;

    @Column(name = "codigo_reporte")
    private Long codigoReporte;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "valor")
    private String valor;

    @Column(name = "estado")
    private String estado;

}
