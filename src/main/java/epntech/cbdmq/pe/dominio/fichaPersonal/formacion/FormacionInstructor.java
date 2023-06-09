package epntech.cbdmq.pe.dominio.fichaPersonal.formacion;

import lombok.Data;

@Data
public class FormacionInstructor {
    private String instructor;
    private String cargo;
    private String rango;
    private String grado;
    private String nombreMateria;
    private Integer codPeriodoAcademico;

    public FormacionInstructor(String instructor, String cargo, String rango, String grado, String nombreMateria, Integer cod_periodo_academico) {
        this.instructor = instructor;
        this.cargo = cargo;
        this.rango = rango;
        this.grado = grado;
        this.nombreMateria = nombreMateria;
        this.codPeriodoAcademico = cod_periodo_academico;
    }
}
