package epntech.cbdmq.pe.dominio.profesionalizacion;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity(name = "pro_materia")
@Table(name ="pro_materia")
@SQLDelete(sql = "UPDATE {h-schema}pro_materia SET estado = 'ELIMINADO' WHERE cod_materia = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ProMateria extends ProfesionalizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_materia")
    private Integer codMateria;

    @Column(name = "nombre_materia")
    private String nombre;

    @Column(name = "cod_eje_materia")
    private Integer codEjeMateria;

    @Column(name = "estado")
    private String estado;

    @Column(name = "es_proyecto")
    private Boolean esProyecto;
}
