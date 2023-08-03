package epntech.cbdmq.pe.dominio.util;

import lombok.Data;

@Data
public class ApiBaseFuncionario {

    private String status;
    private FuncionarioApiDto data;
    private String message;

}
