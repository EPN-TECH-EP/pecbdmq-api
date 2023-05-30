package epntech.cbdmq.pe.dominio.util;

import lombok.Data;

import java.util.Date;

@Data
public class EspecializacionInstructor {
    private String nombreCargo;
    private String nombreRango;
    private String nombreGrado;
    private String nombreTipoInstructor;
    private String instructor;
    private String nombreAula;
    private String nombreTipoCurso;
    private String nombreCatalogoCurso;
    private Date fechaInicioCurso;
    private Date fechaFinCurso;
    private Date fechaInicioCargaNota;
    private Date fechaFinCargaNota;

    public EspecializacionInstructor(String nombreCargo, String nombreRango, String nombreGrado, String nombreTipoInstructor, String instructor, String nombreAula, String nombreTipoCurso, String nombreCatalogoCurso, Date fechaInicioCurso, Date fechaFinCurso, Date fechaInicioCargaNota, Date fechaFinCargaNota) {
        this.nombreCargo = nombreCargo;
        this.nombreRango = nombreRango;
        this.nombreGrado = nombreGrado;
        this.nombreTipoInstructor = nombreTipoInstructor;
        this.instructor = instructor;
        this.nombreAula = nombreAula;
        this.nombreTipoCurso = nombreTipoCurso;
        this.nombreCatalogoCurso = nombreCatalogoCurso;
        this.fechaInicioCurso = fechaInicioCurso;
        this.fechaFinCurso = fechaFinCurso;
        this.fechaInicioCargaNota = fechaInicioCargaNota;
        this.fechaFinCargaNota = fechaFinCargaNota;
    }
}
