package epntech.cbdmq.pe.dominio.util;

import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TipoFaltaPeriodoUtil {
    private Integer cod_falta_periodo;
    private Integer cod_tipo_falta;
    private String nombre_falta;
    private BigDecimal puntaje;

    public TipoFaltaPeriodoUtil(Integer cod_falta_periodo, Integer cod_tipo_falta, String nombre_falta, BigDecimal puntaje) {
        this.cod_falta_periodo = cod_falta_periodo;
        this.cod_tipo_falta = cod_tipo_falta;
        this.nombre_falta = nombre_falta;
        this.puntaje = puntaje;
    }
}
