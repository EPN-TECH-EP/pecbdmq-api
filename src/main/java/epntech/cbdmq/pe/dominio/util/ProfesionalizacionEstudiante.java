package epntech.cbdmq.pe.dominio.util;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class ProfesionalizacionEstudiante {

    private String cedula;
    private String nombre;
    private String apellido;
    private String codigoUnicoEstudiante;
    private String nombreCargo;
    private String nombreRango;
    private String nombreGrado;
    private String nombreMateria;
    private String coordinador;
    private BigDecimal notaMinima;
    private BigDecimal pesoMateria;
    private BigInteger numeroHoras;
    private BigDecimal notaMateria;
    private BigDecimal notaDisciplina;

    public ProfesionalizacionEstudiante(String cedula, String nombre, String apellido, String codigoUnicoEstudiante, String nombreCargo, String nombreRango, String nombreGrado, String nombreMateria, String coordinador, BigDecimal notaMinima, BigDecimal pesoMateria, BigInteger numeroHoras, BigDecimal notaMateria, BigDecimal notaDisciplina) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.codigoUnicoEstudiante = codigoUnicoEstudiante;
        this.nombreCargo = nombreCargo;
        this.nombreRango = nombreRango;
        this.nombreGrado = nombreGrado;
        this.nombreMateria = nombreMateria;
        this.coordinador = coordinador;
        this.notaMinima = notaMinima;
        this.pesoMateria = pesoMateria;
        this.numeroHoras = numeroHoras;
        this.notaMateria = notaMateria;
        this.notaDisciplina = notaDisciplina;
    }
}
