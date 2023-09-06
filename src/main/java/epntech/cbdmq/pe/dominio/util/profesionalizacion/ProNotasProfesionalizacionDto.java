package epntech.cbdmq.pe.dominio.util.profesionalizacion;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ProNotasProfesionalizacionDto {

    @Id
    public Integer codNotaProfesionalizacion;
    private Integer codEstudianteSemestreMateriaParalelo;
    public String nombreParalelo;
    public Integer codEstudiante;
    private String nombre;
    private String apellido;
    private String correoPersonal;
    private Integer codDatosPersonales;
    private Double notaParcial1;
    private Double notaParcial2;
    private Double notaPractica;
    private Double notaAsistencia;
    private Integer codInstructor;
    private Integer codMateria;
    private Integer codSemestre;
    private Double notaMinima;
    private Double pesoMateria;
    private Double numeroHoras;
    private Double notaMateria;
    private Double notaPonderacion;
    private Double notaDisciplina;
    private Double notaSupletorio;
    private String nombrePeriodo;
    private String nombreSemestre;
    private String nombreMateria;
    private String nombreProyecto;
}
