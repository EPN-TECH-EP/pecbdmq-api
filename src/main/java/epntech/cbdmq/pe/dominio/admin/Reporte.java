package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity(name = "gen_reporte")
@Table(name = "gen_reporte")
@SQLDelete(sql = "UPDATE {h-schema}gen_reporte SET estado = 'ELIMINADO' WHERE codigo = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Long codigo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "modulo")
    private String modulo;

    @Column(name = "ruta")
    private String ruta;

    @Column(name = "estado")
    private String estado;

}
