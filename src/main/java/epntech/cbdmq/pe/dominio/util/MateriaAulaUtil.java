package epntech.cbdmq.pe.dominio.util;

import epntech.cbdmq.pe.dominio.admin.Paralelo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class MateriaAulaUtil {
    private Integer codMateria;
    private Integer codAula;
    private BigDecimal ponderacionMateria;
    private BigDecimal notaMinimaSupletorio;
}
