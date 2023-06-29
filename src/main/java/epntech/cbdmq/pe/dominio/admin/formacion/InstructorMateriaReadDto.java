package epntech.cbdmq.pe.dominio.admin.formacion;

import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.util.InstructorDatos;
import lombok.Data;

import java.util.List;

@Data
public class InstructorMateriaReadDto {
    private String nombreMateria;
    private String nombreEjeMateria;
    private String nombreParalelo;
    private Aula aula;
    private InstructorDatos coordinador;
    private List<InstructorDatos> asistentes;
    private List<InstructorDatos> instructores;


}
