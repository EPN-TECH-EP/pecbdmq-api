package epntech.cbdmq.pe.dominio.admin.formacion;

import jakarta.persistence.*;
import lombok.Data;

@Data

@Entity
@Table(name ="gen_instructor_materia_paralelo")
public class InstructorMateriaParalelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_instructor_materia_paralelo")
    private Integer codInstructorMateriaParalelo;
    @Column(name = "cod_instructor")
    private Integer codInstructor;
    @Column(name = "cod_tipo_instructor")
    private Integer codTipoInstructor;
    @Column(name = "cod_materia_paralelo")
    private Integer codMateriaParalelo;
    @Column(name = "estado")
    private String estado;

}
