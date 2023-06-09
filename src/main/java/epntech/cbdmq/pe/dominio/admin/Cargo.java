package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity
@Table(name = "gen_cargo")
@SQLDelete(sql = "UPDATE {h-schema}gen_cargo SET estado = 'ELIMINADO' WHERE cod_cargo = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_cargo")
    private Integer codCargo;
    @Column(name = "nombre_cargo")
    private String nombre;
    @Column(name = "estado")
    private String estado;

}
