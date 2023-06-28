package epntech.cbdmq.pe.dominio.admin;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Table(name = "gen_eje_materia")
@Entity
@Data
public class EjeMateria {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_eje_materia")
    private Long coddEjeMateria;

    @Column(name = "nombre_eje_materia")
    private String nombreEjeMateria;

}
