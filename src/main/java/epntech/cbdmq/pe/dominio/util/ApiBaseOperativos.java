package epntech.cbdmq.pe.dominio.util;

import epntech.cbdmq.pe.dominio.admin.llamamiento.Funcionario;
import lombok.Data;

import java.util.List;

@Data
public class ApiBaseOperativos {
    private String status;
    private List<Funcionario> data;
    private String message;
}
