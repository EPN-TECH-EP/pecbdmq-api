package epntech.cbdmq.pe.dominio.profesionalizacion;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity(name = "pro_nota_profesionalizacion")
@Table(name = "pro_nota_profesionalizacion")
@SQLDelete(sql = "UPDATE {h-schema}pro_nota_profesionalizacion SET estado = 'ELIMINADO' WHERE cod_nota_profesionalizacion = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "estado <> 'ELIMINADO'")
public class ProNotaProfesionalizacion extends ProfesionalizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    @Column(name = "cod_nota_profesionalizacion")
    private Integer codNotaProfesionalizacion;

    @Column(name = "cod_estudiante_semestre_materia_paralelo")
    private Integer codEstudianteSemestreMateriaParalelo;

    @Column(name = "nota_parcial1")
    private Double notaParcial1;

    @Column(name = "nota_parcial2")
    private Double notaParcial2;

    @Column(name = "nota_practica")
    private Double notaPractica;

    @Column(name = "nota_asistencia")
    private Double notaAsistencia;

    @Column(name = "cod_instructor")
    private Integer codInstructor;

    @Column(name = "cod_materia")
    private Integer codMateria;

    @Column(name = "cod_estudiante")
    private Integer codEstudiante;

    @Column(name = "cod_semestre")
    private Integer codSemestre;

    @Column(name = "nota_minima")
    private Double notaMinima;

    @Column(name = "peso_materia")
    private Double pesoMateria;

    @Column(name = "numero_horas")
    private Double numeroHoras;

    @Column(name = "nota_materia")
    private Double notaMateria;

    @Column(name = "nota_ponderacion")
    private Double notaPonderacion;

    @Column(name = "nota_disciplina")
    private Double notaDisciplina;

    @Column(name = "nota_supletorio")
    private Double notaSupletorio;

    @Column(name = "aprobado")
    private Boolean aprobado;

    @Column(name = "estado")
    private String estado;

    @Transient
    private Double asistenciaMinima;
}
