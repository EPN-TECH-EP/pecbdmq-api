package epntech.cbdmq.pe.dominio.util;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class PostulantesAprobadosReprobados {

    private Integer codPostulante;
    private String idPostulante;
    private String cedula;
    private String correoPersonal;
    private String nombre;
    private String apellido;
    private Boolean esAprobado;
}
