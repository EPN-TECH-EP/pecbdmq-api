package epntech.cbdmq.pe.dominio.util.profesionalizacion;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ProPeriodoEstudianteDto {
    @Id
    private Integer codPeriodoEstudiante;
    private Integer codDatosPersonales;
    private Integer codPeriodo;
    private String cedula;
    private String nombre;
    private String apellido;
    private String correoPersonal;
    private String codEstudiante;

}
