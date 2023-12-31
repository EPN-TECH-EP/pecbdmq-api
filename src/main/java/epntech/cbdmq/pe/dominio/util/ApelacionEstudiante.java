package epntech.cbdmq.pe.dominio.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;

@Data
public class ApelacionEstudiante {
    private Integer codApelacion;

    private Date fechaSolicitud;

    private String observacionEstudiante;

    private String observacionInstructor;

    private Boolean aprobacion;

    private Double notaActual;

    private Double notaNueva;

    private String estado;

    private Integer codNotaFormacion;

    private Integer codNotaProfesionalizacion;

    private String nombreMateria;

    private String nombreCompleto;

    private String codigoUnico;

    public ApelacionEstudiante(Integer codApelacion, Date fechaSolicitud, String observacionEstudiante, String observacionInstructor, Boolean aprobacion, Double notaActual, Double notaNueva, String estado, Integer codNotaFormacion, Integer codNotaProfesionalizacion, String nombreMateria, String nombreCompleto, String codigoUnico) {
        this.codApelacion = codApelacion;
        this.fechaSolicitud = fechaSolicitud;
        this.observacionEstudiante = observacionEstudiante;
        this.observacionInstructor = observacionInstructor;
        this.aprobacion = aprobacion;
        this.notaActual = notaActual;
        this.notaNueva = notaNueva;
        this.estado = estado;
        this.codNotaFormacion = codNotaFormacion;
        this.codNotaProfesionalizacion = codNotaProfesionalizacion;
        this.nombreMateria = nombreMateria;
        this.nombreCompleto = nombreCompleto;
        this.codigoUnico = codigoUnico;
    }
}
