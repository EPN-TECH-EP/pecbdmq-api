package epntech.cbdmq.pe.servicio;

import epntech.cbdmq.pe.dominio.admin.Reporte;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface ReporteService {

    List<Reporte> getByModulo(String modulo);

    byte[] getReportePDF(Long codigo);
    void exportAprobadosFormacion(String fileName, String fileType, HttpServletResponse response);

    void exportMallaCurricular(String fileName, String fileType, HttpServletResponse response);

    void exporPeriodosAcademicosPeriodosProfesionalesCursosByYear(String fileName, String fileType, HttpServletResponse response, int year);

    void exportAprobadosEspecializacion(String filename, String filetype, HttpServletResponse response, Integer codCurso);
    void exportAntiguedadesOperativos(String filename, String filetype, HttpServletResponse response) throws Exception;

}
