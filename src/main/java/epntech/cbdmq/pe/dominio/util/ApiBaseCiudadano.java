package epntech.cbdmq.pe.dominio.util;

import lombok.Data;

import java.util.List;

@Data
public class ApiBaseCiudadano {


    private String status;
    private List<CiudadanoApiDto> data;
    private String message;
}
