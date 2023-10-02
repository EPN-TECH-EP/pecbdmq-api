package epntech.cbdmq.pe.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ReporteRequest {
    private String codigoReporte;
    private Date fechaInicio;
    private Date fechaFin;
    private Long codigoCurso;
    private Long codigoPeriodoFormacion;
    private Long codigoPeriodoProfesionalizacion;
}
