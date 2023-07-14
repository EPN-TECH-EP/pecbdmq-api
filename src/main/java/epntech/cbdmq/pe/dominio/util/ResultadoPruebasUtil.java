package epntech.cbdmq.pe.dominio.util;

import lombok.Data;

import java.sql.Time;
@Data
public class ResultadoPruebasUtil {
    private String idPostulante;
    private Double notaPromedioFinal;
    private Boolean cumplePrueba;
}
