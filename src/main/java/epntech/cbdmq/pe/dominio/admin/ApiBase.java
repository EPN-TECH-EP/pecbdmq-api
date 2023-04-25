package epntech.cbdmq.pe.dominio.admin;

import java.util.Optional;

import lombok.Data;

@Data
public class ApiBase {

	private String status;
    private Optional<?> data;
    private String message;

}
