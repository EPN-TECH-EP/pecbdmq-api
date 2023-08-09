package epntech.cbdmq.pe.dominio.util;

import lombok.Data;

@Data
public class DatosInscripcionEsp {
    private Integer codEstudiante;
    private String codUnicoEstudiante;
    private String cedula;
    private String nombre;
    private String apellido;
    private String correoPersonal;
    private String correoInstitucional;

    public DatosInscripcionEsp(Integer codEstudiante, String codUnicoEstudiante, String cedula, String nombre, String apellido, String correoPersonal, String correoInstitucional) {
        this.codEstudiante = codEstudiante;
        this.codUnicoEstudiante = codUnicoEstudiante;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoPersonal = correoPersonal;
        this.correoInstitucional = correoInstitucional;
    }
}
