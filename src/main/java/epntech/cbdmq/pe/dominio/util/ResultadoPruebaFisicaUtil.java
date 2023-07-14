package epntech.cbdmq.pe.dominio.util;

import jakarta.persistence.Column;
import lombok.Data;

import java.sql.Time;
@Data
public class ResultadoPruebaFisicaUtil {
    private String idPostulante;
    private Double notaPromedioFinal;
    private Integer resultado;
    private Time resultadoTiempo;

}
