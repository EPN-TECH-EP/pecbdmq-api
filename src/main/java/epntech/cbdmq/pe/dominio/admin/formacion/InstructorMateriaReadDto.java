package epntech.cbdmq.pe.dominio.admin.formacion;

import epntech.cbdmq.pe.dominio.fichaPersonal.Instructor;
import epntech.cbdmq.pe.dominio.util.InstructorDatos;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InstructorMateriaReadDto {
    private Integer codMateria;
    private BigDecimal notaMinimaSupletorio;
    private BigDecimal ponderacionMateria;
    private String nombre;
    private String nombreEje;
    private InstructorDatos[] instructores;
    private InstructorDatos[] asistentes;
    private InstructorDatos coordinador;
    private Integer codAula;
    private String nombreAula;
    private Integer codParalelo;
    private String nombreParalelo;

    public InstructorMateriaReadDto(Integer codMateria,BigDecimal notaMinimaSupletorio, BigDecimal ponderacionMateria,String nombre, String nombreEje, Integer codAula, String nombreAula, Integer codParalelo, String nombreParalelo) {
        this.codMateria = codMateria;
        this.notaMinimaSupletorio=notaMinimaSupletorio;
        this.ponderacionMateria= ponderacionMateria;
        this.nombre = nombre;
        this.nombreEje = nombreEje;
        this.codAula = codAula;
        this.nombreAula = nombreAula;
        this.codParalelo= codParalelo;
        this.nombreParalelo = nombreParalelo;
        this.asistentes = new InstructorDatos[0];
        this.instructores = new InstructorDatos[0];
    }
}
