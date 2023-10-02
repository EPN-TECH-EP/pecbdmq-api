package epntech.cbdmq.pe.dominio.admin.formacion;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.util.MateriaParaleloDocumento;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class MateriaDocumentoDto {
    private Integer codMateriaParaleloDocumento;
    private Integer codDocumento;
    private Integer codMateriaParalelo;
    private Boolean esTarea;
    private String ruta;
    private String nombre;

    public MateriaDocumentoDto(Integer codMateriaParaleloDocumento, Integer codDocumento, Integer codMateriaParalelo, Boolean esTarea, String ruta, String nombre) {
        this.codMateriaParaleloDocumento = codMateriaParaleloDocumento;
        this.codDocumento = codDocumento;
        this.codMateriaParalelo = codMateriaParalelo;
        this.esTarea = esTarea;
        this.ruta = ruta;
        this.nombre = nombre;
    }
}
