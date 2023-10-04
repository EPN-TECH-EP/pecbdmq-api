package epntech.cbdmq.pe.dominio.admin.formacion;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class MateriaParaleloDto {
    private Integer codMateria;
    private Integer codMateriaParalelo;
    private Integer codEjeMateria;
    private String nombre;
    private String estado;

    public MateriaParaleloDto(Integer codMateria, Integer codMateriaParalelo, Integer codEjeMateria, String nombre, String estado) {
        this.codMateria = codMateria;
        this.codMateriaParalelo = codMateriaParalelo;
        this.codEjeMateria = codEjeMateria;
        this.nombre = nombre;
        this.estado = estado;
    }
}
