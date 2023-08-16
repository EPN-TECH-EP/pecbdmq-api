package epntech.cbdmq.pe.dominio.util.profesionalizacion;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ProConvocatoriaRequisitoDto {
    @Id
    private Integer codigo;
    private Integer codigoRequisito;
    private Integer codigoConvocatoria;
    private String nombreRequisito;
    private String nombreConvocatoria;
}
