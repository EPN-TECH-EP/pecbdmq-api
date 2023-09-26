package epntech.cbdmq.pe.dominio.util;

import lombok.Data;

import java.util.List;

@Data
public class ApiBaseOperativos {
    private String status;
    private List<OperativoApiDto> data;
    private String message;
}
