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
    private Long codMateriaEstudiante;

    @Column(name = "cod_materia")
    private Long codMateria;

    @JoinColumn(name = "cod_estudiante")
    private Long codEstudiante;

    @JoinColumn(name = "cod_periodo_academico")
    private Long codPeriodoAcademico;
    
    @JoinColumn(name="cod_semestre")
    private Long codSemestre;
}
