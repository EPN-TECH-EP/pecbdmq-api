package epntech.cbdmq.pe.servicio.reporteria;

import jakarta.servlet.http.HttpServletResponse;

import java.util.Date;

public interface ReporteService {

    void exportAprobadosFormacion(String fileName, String fileType, HttpServletResponse response);

    void exportMallaCurricular(String fileName, String fileType, HttpServletResponse response);

    void exporPeriodosAcademicosPeriodosProfesionalesCursosByYear(String fileName, String fileType, HttpServletResponse response, Date startDate, Date endDate);
    public void exportAprobadosEspecializacion(String filename, String filetype, HttpServletResponse response, Integer codCurso);
}
