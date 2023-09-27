package epntech.cbdmq.pe.servicio;

import epntech.cbdmq.pe.dominio.admin.Reporte;

import java.util.List;

public interface ReporteService {

    List<Reporte> getByModulo(String modulo);

    byte[] getReportePDF(Long codigo);

}
