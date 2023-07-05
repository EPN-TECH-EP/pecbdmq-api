package epntech.cbdmq.pe.dominio.admin.formacion;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class InformacionMateriaDto {

    private Long codMateriaParalelo;
    private String nombreMateria;
    private String nombreEjeMateria;
    private String nombreParalelo;
    private Long codAula;

    public InformacionMateriaDto(Long codMateriaParalelo, String nombreMateria, String nombreEjeMateria, String nombreParalelo, Long codAula) {
        this.codMateriaParalelo = codMateriaParalelo;
        this.nombreMateria = nombreMateria;
        this.nombreEjeMateria = nombreEjeMateria;
        this.nombreParalelo = nombreParalelo;
        this.codAula = codAula;
    }

}
