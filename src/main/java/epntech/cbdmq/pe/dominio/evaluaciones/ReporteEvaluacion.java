package epntech.cbdmq.pe.dominio.evaluaciones;

import lombok.Data;

@Data
public class ReporteEvaluacion {
    private String pregunta;
    private Integer respuestaSi;
    private Integer respuestaNo;
}
