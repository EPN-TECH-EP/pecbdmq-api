package epntech.cbdmq.pe.dominio.util.profesionalizacion;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ProDelegadoDatosDto {
    @Id
    private Integer codDelegado;
    private String cedula;
    private String nombre;
    private String apellido;
    private String correoPersonal;
    private Integer codDatosPersonales;
}
