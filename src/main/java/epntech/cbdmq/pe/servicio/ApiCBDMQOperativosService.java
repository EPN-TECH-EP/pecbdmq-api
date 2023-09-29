package epntech.cbdmq.pe.servicio;

import epntech.cbdmq.pe.dominio.admin.llamamiento.Funcionario;
import epntech.cbdmq.pe.dominio.util.OperativoApiDto;

import java.util.List;

public interface ApiCBDMQOperativosService {
    List<Funcionario> servicioOperativosAndNoOperativos() throws Exception;
    List<Funcionario> servicioOperativos() throws Exception;
    List<Funcionario> servicioOperativosOrderByAntiguedad() throws Exception;
    void notificarMejoresProspectos(int numeroLimite) throws Exception;
    void  notificarOperativoComoMejorProspecto(String correo);
    Boolean guardarInBD() throws Exception;
}
