package epntech.cbdmq.pe.dominio.admin;

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
public class Rango {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_rango")
    private Integer codRango;
    @Column(name = "nombre_rango")
    private String nombre;
    @Column(name = "estado")
    private String estado;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cod_grado")
    private Grado codGrado;
}
