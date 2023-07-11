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
    private Integer cod_aula;
    private String nombre_aula;

    public InstructorMateriaReadDto(Integer codMateria, String nombre, String nombreEje, Integer cod_aula, String nombre_aula) {
        this.codMateria = codMateria;
        this.nombre = nombre;
        this.nombreEje = nombreEje;
        this.cod_aula = cod_aula;
        this.nombre_aula = nombre_aula;
    }
}
