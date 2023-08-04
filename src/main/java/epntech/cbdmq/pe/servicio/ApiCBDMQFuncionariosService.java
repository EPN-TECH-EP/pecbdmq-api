package epntech.cbdmq.pe.servicio;

import epntech.cbdmq.pe.dominio.util.FuncionarioApiDto;

import java.util.Optional;

public interface ApiCBDMQFuncionariosService {
    Optional<FuncionarioApiDto> servicioFuncionarios(String cedula) throws Exception;
}
