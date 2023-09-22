package epntech.cbdmq.pe.dominio.fichaPersonal.formacion;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class EstudianteMateriaDocumentoDto {
    private Integer codMateriaParalelo;
    private Integer codEstudiante;
    private String estado;
    private String descripcion;

}
