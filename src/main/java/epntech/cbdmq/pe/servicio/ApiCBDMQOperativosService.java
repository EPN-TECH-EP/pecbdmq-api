package epntech.cbdmq.pe.servicio;

import epntech.cbdmq.pe.dominio.util.FuncionarioApiDto;
import epntech.cbdmq.pe.dominio.util.OperativoApiDto;

import java.util.List;

public interface ApiCBDMQOperativosService {
    List<OperativoApiDto> servicioOperativos() throws Exception;
}
