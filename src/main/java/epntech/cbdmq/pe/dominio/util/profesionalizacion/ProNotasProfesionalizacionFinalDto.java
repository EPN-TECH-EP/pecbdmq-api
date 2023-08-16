package epntech.cbdmq.pe.dominio.util.profesionalizacion;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ProNotasProfesionalizacionFinalDto {
    @Id
    public Integer codNotaProfesionalizacionFinal;
    private Integer codEstudiante;
    private Integer codSemestre;
    private Double promedioDisciplinaInstructor;
    private Double promedioDisciplinaOficialSemana;
    private Double promedioAcademico;
    private Double promedioDiscipinaFinal;
    private Double ponderacionAcademica;
    private Double ponderacionDisciplina;
    private Double notaFinalAcademia;
    private Double notaFinalDisciplina;
    private Double notaFinal;
    private Boolean realizoEncuesta;
    private Boolean aprobado;
    private Double notaParcial1;
    private Double notaParcial2;
    private Double notaPractica;
    private Double notaAsistencia;
    private Integer codPeriodo;
    private String nombre;
    private String apellido;
    private String correoPersonal;
    private Integer codDatosPersonales;

}
