package epntech.cbdmq.pe.dominio.admin.formacion;

import epntech.cbdmq.pe.dominio.fichaPersonal.Instructor;
import lombok.Data;

@Data
public class InstructorMateriaReadDto {
    private Integer codMateria;
    private String nombre;
    private String nombreEje;
    private Instructor[] instructores;
    private Instructor[] asistentes;
    private Instructor coordinador;
    private Integer codAula;
    private String nombreAula;

    public InstructorMateriaReadDto(Integer codMateria, String nombre, String nombreEje, Integer codAula, String nombreAula) {
        this.codMateria = codMateria;
        this.nombre = nombre;
        this.nombreEje = nombreEje;
        this.codAula = codAula;
        this.nombreAula = nombreAula;
        this.asistentes = new Instructor[0];
        this.instructores = new Instructor[0];
    }
}
