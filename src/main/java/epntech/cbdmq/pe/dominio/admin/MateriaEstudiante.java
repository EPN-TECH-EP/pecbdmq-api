package epntech.cbdmq.pe.dominio.admin;

import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name ="gen_materia_estudiante")
public class MateriaEstudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_materia_estudiante")
    private Integer codMateriaEstudiante;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cod_materia")
    private Materia materia;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cod_estudiante")
    private Estudiante estudiante;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cod_periodo_academico")
    private PeriodoAcademico codPeriodoAcademico;
    @OneToOne(cascade =CascadeType.ALL)
    @JoinColumn(name="cod_semestre")
    private Semestre semestre;
}
