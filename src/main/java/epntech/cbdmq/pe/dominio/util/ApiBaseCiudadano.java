package epntech.cbdmq.pe.dominio.util;

import lombok.Data;

@Data
public class ApiBaseCiudadano {


    private String status;
    private CiudadanoApiDto data;
    private String message;
}
