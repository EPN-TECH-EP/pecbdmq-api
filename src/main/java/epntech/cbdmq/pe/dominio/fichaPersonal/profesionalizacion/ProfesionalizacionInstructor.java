package epntech.cbdmq.pe.dominio.fichaPersonal.profesionalizacion;

import lombok.Data;

@Data
public class ProfesionalizacionInstructor {

    private String intructor;
    private String nombreCargo;
    private String nombreRango;
    private String nombreGrado;
    private String nombreMateria;
    private String semestre;

    public ProfesionalizacionInstructor(String intructor, String nombreCargo, String nombreRango, String nombreGrado, String nombreMateria, String semestre) {
        this.intructor = intructor;
        this.nombreCargo = nombreCargo;
        this.nombreRango = nombreRango;
        this.nombreGrado = nombreGrado;
        this.nombreMateria = nombreMateria;
        this.semestre = semestre;
    }
}
