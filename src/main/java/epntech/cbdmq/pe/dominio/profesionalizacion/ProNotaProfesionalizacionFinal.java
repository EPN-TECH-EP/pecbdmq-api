package epntech.cbdmq.pe.dominio.profesionalizacion;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity(name = "pro_nota_profesionalizacion_final")
@Table(name = "pro_nota_profesionalizacion_final")
@SQLDelete(sql = "UPDATE {h-schema}pro_nota_profesionalizacion_final SET estado = 'ELIMINADO' WHERE cod_nota_profesionalizacion_final = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ProNotaProfesionalizacionFinal extends ProfesionalizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    @Column(name = "cod_nota_profesionalizacion_final")
    private Integer codNotaProfesionalizacionFinal;

    @Column(name = "cod_estudiante_semestre")
    private Integer codEstudianteSemestre;

    @Column(name = "nota_parcial_1")
    private Double notaParcial1;

    @Column(name = "nota_parcial_2")
    private Double notaParcial2;

    @Column(name = "nota_practica")
    private Double notaPractica;

    @Column(name = "nota_asistencia")
    private Double notaAsistencia;





    @Column(name = "cod_estudiante")
    private Integer codEstudiante;

    @Column(name = "cod_semestre")
    private Integer codSemestre;

    @Column(name = "promedio_disciplina_instructor")
    private Double promedioDisciplinaInstructor;

    @Column(name = "promedio_disciplina_oficial_semana")
    private Double promedioDisciplinaOficialSemana;

    @Column(name = "promedio_academico")
    private Double promedioAcademico;

    @Column(name = "promedio_disciplina_final")
    private Double promedioDisciplinaFinal;

    @Column(name = "ponderacion_academica")
    private Double ponderacionAcademica;

    @Column(name = "ponderacion_disciplina")
    private Double ponderacionDisciplina;

    @Column(name = "nota_final_academica")
    private Double notaFinalAcademica;

    @Column(name = "nota_final_disciplina")
    private Double notaFinalDisciplina;

    @Column(name = "nota_final")
    private Double notaFinal;

    @Column(name = "realizo_encuesta")
    private Boolean realizoEncuesta;

    @Column(name = "aprobado")
    private Boolean aprobado;

    @Column(name = "estado")
    private String estado;


}
