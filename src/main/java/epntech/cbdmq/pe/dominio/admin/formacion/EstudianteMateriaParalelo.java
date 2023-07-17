package epntech.cbdmq.pe.dominio.admin.formacion;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name ="gen_estudiante_materia_paralelo")
public class EstudianteMateriaParalelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_estudiante_materia_paralelo")
    private Integer codEstudianteMateriaParalelo;
    @Column(name = "cod_materia_paralelo")
    private Integer codMateriaParalelo;
    @Column(name = "cod_estudiante")
    private Integer codEstudiante;
    @Column(name = "estado")
    private String estado;
}
