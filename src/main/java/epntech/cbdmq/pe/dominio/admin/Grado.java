package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Data;

@Data
@Entity
@Table(name = "gen_grado")
@SQLDelete(sql = "UPDATE {h-schema}gen_grado SET estado = 'ELIMINADO' WHERE cod_grado = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Grado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_grado")
    private Integer codGrado;
    @Column(name = "nombre_grado")
    private String nombre;
    @Column(name = "estado")
    private String estado;

}
