package epntech.cbdmq.pe.servicio;

import epntech.cbdmq.pe.dominio.util.OperativoApiDto;

import java.util.List;

public interface ApiCBDMQOperativosService {
    List<OperativoApiDto> servicioOperativosAndNoOperativos() throws Exception;
    List<OperativoApiDto> servicioOperativos() throws Exception;
    List<OperativoApiDto> servicioOperativosOrderByAntiguedad() throws Exception;
    void notificarMejoresProspectos(int numeroLimite) throws Exception;
    void  notificarOperativoComoMejorProspecto(String correo);
}
