package epntech.cbdmq.pe.servicio;

import epntech.cbdmq.pe.dto.ReporteRequest;
import epntech.cbdmq.pe.dto.ReporteResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface ReporteService {

    ReporteResponse getReporte(String codigo);

    byte[] getReportePDF(ReporteRequest request);

    byte[] getReporteExcel(ReporteRequest request);

    void exportAprobadosFormacion(String fileName, String fileType, HttpServletResponse response);

    void exportMallaCurricular(String fileName, String fileType, HttpServletResponse response);

    void exporPeriodosAcademicosPeriodosProfesionalesCursosByYear(String fileName, String fileType, HttpServletResponse response, int year);

    void exportAprobadosEspecializacion(String filename, String filetype, HttpServletResponse response, Integer codCurso);
    void exportAntiguedadesOperativos(String filename, String filetype, HttpServletResponse response) throws Exception;
    void exportReporteEncuestasESP(String filename, String filetype, HttpServletResponse response,Integer codCurso) throws Exception;
    void exportReporteFichaPersonal(String filename, String filetype, HttpServletResponse response, Integer codEstudianteFor,Integer codEstudianteEsp) throws Exception;

}
