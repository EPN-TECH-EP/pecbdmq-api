package epntech.cbdmq.pe.servicio.impl;

import epntech.cbdmq.pe.dominio.admin.Reporte;
import epntech.cbdmq.pe.dominio.admin.ReporteParametro;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import epntech.cbdmq.pe.repositorio.ReporteParametroRepository;
import epntech.cbdmq.pe.repositorio.admin.ReporteRepository;
import epntech.cbdmq.pe.servicio.ReporteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReporteServiceImpl implements ReporteService {

    private final ReporteRepository reporteRepository;
    private final ReporteParametroRepository reporteParametroRepository;
    private final DataSource dataSource;

    @Override
    public List<Reporte> getByModulo(String modulo) {
        return reporteRepository.findByModulo(modulo);
    }

    @Override
    public byte[] getReportePDF(Long codigo) {
        Reporte reporte = reporteRepository.findById(codigo).orElseThrow(() -> new BusinessException("No existe el reporte"));
        List<ReporteParametro> listaParametros = reporteParametroRepository.findByCodigoReporte(codigo);

        Map<String, Object> parametros = new HashMap<>();
        for (ReporteParametro parametro : listaParametros) {
            parametros.put(parametro.getNombre(), parametro.getValor());
        }

        parametros.put("id", codigo);
        return getReporte(parametros, reporte.getRuta(), reporte.getNombre());
    }

    private byte[] getReporte(Map<String, Object> parametros, String ruta, String nombre) {
        try (Connection con = dataSource.getConnection()) {
            JasperPrint print = JasperFillManager.fillReport(ruta, parametros, con);
            return JasperExportManager.exportReportToPdf(print);
        } catch (Exception ex) {
            log.error("Error al generar el reporte [" + nombre +"]: ", ex);
            throw new BusinessException("Error al generar el reporte [" + nombre +"]");
        }
    }

}
