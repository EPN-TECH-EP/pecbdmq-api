package epntech.cbdmq.pe.dominio.admin.formacion;

import lombok.Data;

@Data
public class NotaMateriaByEstudiante {
    private Integer codNotaFormacion;
    private Integer codMateriaCurso;
    private String nombreMateria;
    private Double notaMateria;
    private Double notaDisciplina;
    private Double notaSupletorio;
    private Integer codInstructor;
    private String nombreCompletoInstructor;
    private String nombrePeriodoFormacion;

    public NotaMateriaByEstudiante(Integer codNotaFormacion, Integer codMateriaCurso, String nombreMateria, Double notaMateria, Double notaDisciplina, Double notaSupletorio, Integer codInstructor, String nombreCompletoInstructor, String nombrePeriodoFormacion) {
        this.codNotaFormacion = codNotaFormacion;
        this.codMateriaCurso = codMateriaCurso;
        this.nombreMateria = nombreMateria;
        this.notaMateria = notaMateria;
        this.notaDisciplina = notaDisciplina;
        this.notaSupletorio = notaSupletorio;
        this.codInstructor = codInstructor;
        this.nombreCompletoInstructor = nombreCompletoInstructor;
        this.nombrePeriodoFormacion = nombrePeriodoFormacion;
    }
}
