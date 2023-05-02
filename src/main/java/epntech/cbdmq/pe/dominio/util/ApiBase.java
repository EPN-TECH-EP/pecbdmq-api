package epntech.cbdmq.pe.dominio.util;

import java.util.Optional;

import lombok.Data;

@Data
public class ApiBase {

	private String status;
    private Optional<?> data;
    private String message;

}
