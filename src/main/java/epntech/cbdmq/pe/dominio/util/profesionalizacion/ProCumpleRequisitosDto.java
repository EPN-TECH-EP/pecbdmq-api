package epntech.cbdmq.pe.dominio.util.profesionalizacion;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ProCumpleRequisitosDto {
    @Id
    private Integer codCumpleRequisito;
    private Integer codInscripcion;
    private Integer codRequisito;
    private String nombreRequisito;
    private Boolean cumple;
    private String observaciones;
    private String observacionMuestra;

}
