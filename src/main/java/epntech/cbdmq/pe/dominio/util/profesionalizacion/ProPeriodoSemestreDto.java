package epntech.cbdmq.pe.dominio.util.profesionalizacion;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ProPeriodoSemestreDto {
    @Id
    public Integer codPeriodoSemestre;
    public Integer codPeriodo;
    public Integer codSemestre;
    public String nombrePeriodo;
    public String nombreSemestre;

}
