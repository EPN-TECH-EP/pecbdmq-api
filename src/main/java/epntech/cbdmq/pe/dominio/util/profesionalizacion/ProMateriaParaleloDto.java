package epntech.cbdmq.pe.dominio.util.profesionalizacion;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ProMateriaParaleloDto {
    @Id
    private Integer codSemestreMateriaParalelo;
    private Integer codSemestreMateria;
    private Integer codParalelo;
    private String nombreParalelo;
    private String nombreMateria;
    private Integer codProyecto;
    private String nombreProyecto;
    private Boolean esProyecto;

}
