package epntech.cbdmq.pe.dominio.admin.formacion;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name ="gen_estudiante_materia_documento")
public class EstudianteMateriaDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_documento_estudiante_materia_documento")
    private Integer codEstudianteMateriaDocumento;
    @Column(name = "cod_documento")
    private Integer codDocumento;
    @Column(name = "cod_estudiante_materia_paralelo")
    private Integer codEstudianteMateriaParalelo;
    @Column(name = "estado")
    private String estado;
    @Column(name = "descripcion")
    private String descripcion;
}
