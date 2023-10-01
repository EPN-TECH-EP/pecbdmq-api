package epntech.cbdmq.pe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ReporteResponse {
    private String nombre;
    private String descripcion;
    private Boolean verFechas;
    private Boolean verSelectPromocion;
    private Boolean verSelectPeriodo;
    private Boolean verSelectCurso;
}
