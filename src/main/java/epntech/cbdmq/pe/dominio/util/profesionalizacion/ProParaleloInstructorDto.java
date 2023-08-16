package epntech.cbdmq.pe.dominio.util.profesionalizacion;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ProParaleloInstructorDto {
    @Id
    private Integer codPeriodoSemestreMateriaParaleloInstructor;
    private Integer codPeriodoSemestreMateriaParalelo;
    private Integer codInstructor;
    private String nombreParalelo;
    private String nombre;
    private String apellido;
    private String correoPersonal;
    private Integer codDatosPersonales;
    private String nombreCatalogo;
}
