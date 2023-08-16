package epntech.cbdmq.pe.dominio.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.Documento;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity(name = "pro_nota_profesionalizacion_general")
@Table(name = "pro_nota_profesionalizacion_general")
@SQLDelete(sql = "UPDATE {h-schema}pro_nota_profesionalizacion_general SET estado = 'ELIMINADO' WHERE codigo = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ProNotaProfesionalizacionGeneral extends ProfesionalizacionEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    @Column(name = "codigo")
    private Integer codigo;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "cod_materia_paralelo")
    private Integer codMateriaParalelo;

    @Column(name = "estado")
    private String estado;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "pro_nota_profesionalizacion_documento",
            joinColumns = @JoinColumn(name = "cod_nota_profesionalizacion"),
            inverseJoinColumns = @JoinColumn(name = "cod_documento")
    )
    private List<Documento> documentos = new ArrayList<>();
}
