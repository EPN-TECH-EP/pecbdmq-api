package epntech.cbdmq.pe.dominio.util;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
@Data
public class EspecializacionEstudiante {
    private String tipoCurso;
    private String nombreCurso;
    private Integer notaMinima;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String estado;
    private String usuarioCalificador;
    private BigDecimal notaFinal;
    private boolean notaResultado;
    private String tipoInstructor;
    private String nombreInstructor;
    private String aula;


    public EspecializacionEstudiante(String tipoCurso, String nombreCurso, Integer notaMinima, LocalDateTime fechaInicio, LocalDateTime fechaFin, String estado, String usuarioCalificador, BigDecimal notaFinal, boolean notaResultado, String tipoInstructor, String nombreInstructor, String aula) {
        this.tipoCurso = tipoCurso;
        this.nombreCurso = nombreCurso;
        this.notaMinima = notaMinima;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.usuarioCalificador = usuarioCalificador;
        this.notaFinal = notaFinal;
        this.notaResultado = notaResultado;
        this.tipoInstructor = tipoInstructor;
        this.nombreInstructor = nombreInstructor;
        this.aula = aula;
    }
}
