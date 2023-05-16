package epntech.cbdmq.pe.dominio.util;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FormacionEstudiante {
    private String materia;
    private String instructor;
    private BigDecimal porcentajeFinal;
    private BigDecimal porcentajeNotaMateria;
    private Double porcentajeComponenteNota;
    private String tipoNota;
    private Integer aporteAcademico;
    private Double notaFinalFormacion;
    private Boolean resultado;

    public FormacionEstudiante(String materia, String instructor, BigDecimal porcentajeFinal, BigDecimal porcentajeNotaMateria, Double porcentajeComponenteNota, String tipoNota, Integer aporteAcademico, Double notaFinalFormacion, Boolean resultado) {
        this.materia = materia;
        this.instructor = instructor;
        this.porcentajeFinal = porcentajeFinal;
        this.porcentajeNotaMateria = porcentajeNotaMateria;
        this.porcentajeComponenteNota = porcentajeComponenteNota;
        this.tipoNota = tipoNota;
        this.aporteAcademico = aporteAcademico;
        this.notaFinalFormacion = notaFinalFormacion;
        this.resultado = resultado;
    }
}
