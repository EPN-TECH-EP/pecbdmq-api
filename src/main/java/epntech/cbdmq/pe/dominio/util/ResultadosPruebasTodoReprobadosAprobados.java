package epntech.cbdmq.pe.dominio.util;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Data
public class ResultadosPruebasTodoReprobadosAprobados {
    private Integer codPostulante;
    private String idPostulante;
    private String cedula;
    private String nombre;
    private String apellido;
    private String correoPersonal;

    private Integer resultado;
    private String resultadoTiempo;
    private Boolean cumplePrueba;
    private BigDecimal notaPromedioFinal;
    private Boolean esAprobado;
}
