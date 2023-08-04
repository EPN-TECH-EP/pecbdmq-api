package epntech.cbdmq.pe.dominio.util;

import lombok.Data;

@Data
public class ParamsValidacion {
    private Boolean aprueba;
    private String observacion;
    private Long codUsuarioAprueba;
}
