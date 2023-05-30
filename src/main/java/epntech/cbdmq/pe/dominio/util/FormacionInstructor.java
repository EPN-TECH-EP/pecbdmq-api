package epntech.cbdmq.pe.dominio.util;

import lombok.Data;

@Data
public class FormacionInstructor {
    private String instructor;
    private String cargo;
    private String rango;
    private String grado;
    private String nombreMateria;
    private Integer cod_periodo_academico;

    public FormacionInstructor(String instructor, String cargo, String rango, String grado, String nombreMateria, Integer cod_periodo_academico) {
        this.instructor = instructor;
        this.cargo = cargo;
        this.rango = rango;
        this.grado = grado;
        this.nombreMateria = nombreMateria;
        this.cod_periodo_academico = cod_periodo_academico;
    }
}
