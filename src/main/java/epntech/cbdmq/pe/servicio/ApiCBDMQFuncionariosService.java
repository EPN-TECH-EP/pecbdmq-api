package epntech.cbdmq.pe.servicio;

import java.util.Optional;

public interface ApiCBDMQFuncionariosService {
    Optional<?> servicioFuncionarios(String cedula) throws Exception;
}
