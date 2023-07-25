package epntech.cbdmq.pe.dominio.util;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TipoFaltaPeriodoUtil {
    private Integer codFaltaPeriodo;
    private Integer codTipoFalta;
    private String nombreFalta;
    private BigDecimal puntaje;

    public TipoFaltaPeriodoUtil(Integer codFaltaPeriodo, Integer codTipoFalta, String nombreFalta, BigDecimal puntaje) {
        this.codFaltaPeriodo = codFaltaPeriodo;
        this.codTipoFalta = codTipoFalta;
        this.nombreFalta = nombreFalta;
        this.puntaje = puntaje;
    }
}
