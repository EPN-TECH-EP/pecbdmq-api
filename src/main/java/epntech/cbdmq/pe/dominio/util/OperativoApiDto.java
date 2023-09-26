package epntech.cbdmq.pe.dominio.util;

import lombok.Data;

import java.util.Date;

@Data
public class OperativoApiDto {
    private String type;
    private String agrupacion;
    private String apellidos;
    private String email;
    private Date fechaIngreso;
    private String nombres;
    private Boolean operativo;
    private String pin;
}
