package epntech.cbdmq.pe.dominio.util.profesionalizacion;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ProInscripcionesDelegadosDto {
    @Id
    private Integer codInscripcionesDelegados;
    private Integer codInscripciones;
    private Integer codDelegados;
    private Integer codEstudiante;
    private Integer codDatosPersonales;
    private String nombreEstudiante;
    private String apellidoEstudiante;
    private String correoPersonalEstudiante;
    private Integer codDatosPersonalesEstudiante;
    private String nombreDelegado;
    private String apellidoDelegado;
    private String correoPersonalDelegado;
    private Integer codDatosPersonalesDelegado;
    private String cedulaEstudiante;
}
