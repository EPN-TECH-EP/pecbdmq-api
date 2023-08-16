package epntech.cbdmq.pe.dominio.util.profesionalizacion;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ProParaleloEstudianteDto {
    @Id
    public Integer codParaleloEstudiante;
    public Integer codMateriaParalelo;
    public String nombreParalelo;
    public Integer codEstudiante;
    private String nombre;
    private String apellido;
    private String correoPersonal;
    private Integer codDatosPersonales;
    private String cedula;
    private Integer codProyecto;
    private String nombreCatalogo;
}
