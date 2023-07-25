package epntech.cbdmq.pe.dominio.admin.formacion;

import lombok.Data;

@Data
public class NotaMateriaByEstudiante {
    private Integer codNotaFormacion;
    private String nombreMateria;
    private Double notaMateria;
    private Double notaDisciplina;
    private Double notaSupletorio;
    private Integer codInstructor;
    private String nombreCompletoInstructor;

    public NotaMateriaByEstudiante(Integer codNotaFormacion, String nombreMateria, Double notaMateria, Double notaDisciplina, Double notaSupletorio, Integer codInstructor, String nombreCompletoInstructor) {
        this.codNotaFormacion = codNotaFormacion;
        this.nombreMateria = nombreMateria;
        this.notaMateria = notaMateria;
        this.notaDisciplina = notaDisciplina;
        this.notaSupletorio = notaSupletorio;
        this.codInstructor = codInstructor;
        this.nombreCompletoInstructor = nombreCompletoInstructor;
    }
}
