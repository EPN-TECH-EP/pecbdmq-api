package epntech.cbdmq.pe.servicio;

import epntech.cbdmq.pe.dominio.admin.llamamiento.Funcionario;
import epntech.cbdmq.pe.dominio.util.OperativoApiDto;

import java.util.List;

public interface ApiCBDMQOperativosService {
    List<Funcionario> servicioOperativosAndNoOperativos() throws Exception;
    Boolean guardarInBD() throws Exception;
}
