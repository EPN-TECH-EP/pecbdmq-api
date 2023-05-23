package epntech.cbdmq.pe.dominio.util;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FormacionEstudiante {
    private String cedula;
    private String nombres;
    private String apellidos;
    private String codigoUnico;
    private String nombreCargo;
    private String nombreRango;
    private String nombreGrado;
    private String materia;
    private String instructor;
    private BigDecimal porcentajeFinal;
    private BigDecimal notaMinima;
    private BigDecimal pesoMateria;
    private Integer numeroHoras;
    private BigDecimal notaMateria;
    private BigDecimal notaPonderacion;
    private BigDecimal notaDisciplina;

    public FormacionEstudiante(String cedula, String nombres, String apellidos, String codigoUnico, String nombreCargo, String nombreRango, String nombreGrado, String materia, String instructor, BigDecimal porcentajeFinal, BigDecimal notaMinima, BigDecimal pesoMateria, Integer numeroHoras, BigDecimal notaMateria, BigDecimal notaPonderacion, BigDecimal notaDisciplina) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.codigoUnico = codigoUnico;
        this.nombreCargo = nombreCargo;
        this.nombreRango = nombreRango;
        this.nombreGrado = nombreGrado;
        this.materia = materia;
        this.instructor = instructor;
        this.porcentajeFinal = porcentajeFinal;
        this.notaMinima = notaMinima;
        this.pesoMateria = pesoMateria;
        this.numeroHoras = numeroHoras;
        this.notaMateria = notaMateria;
        this.notaPonderacion = notaPonderacion;
        this.notaDisciplina = notaDisciplina;
    }
}
