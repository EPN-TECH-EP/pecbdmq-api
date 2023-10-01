package epntech.cbdmq.pe.resource;

import epntech.cbdmq.pe.dto.ReporteRequest;
import epntech.cbdmq.pe.dto.ReporteResponse;
import epntech.cbdmq.pe.servicio.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
public class ReporteResource {
    private final ReporteService service;

    @GetMapping
    public ResponseEntity<ReporteResponse> getReporte(@RequestParam("codigo") String codigo) {
        return new ResponseEntity<>(service.getReporte(codigo), HttpStatus.OK);
    }

    @PostMapping(value = "/generar-pdf", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getReportePdf(@RequestBody ReporteRequest request) {
        byte[] data = service.getReportePDF(request);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping(value = "/generar-excel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getReporteExcel(@RequestBody ReporteRequest request) {
        byte[] data = service.getReporteExcel(request);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping(value = "/generarReporteAprobadosReprobados/{fileName}/{fileType}")
    public void downloadFormacion(
            @PathVariable String fileName,
            @PathVariable String fileType,
            @Autowired HttpServletResponse response
    ) {
        service.exportAprobadosFormacion(fileName, fileType, response);
    }

    @PostMapping(value = "/generarReporteAprobadosReprobados/{codCurso}/{fileName}/{fileType}")
    public void downloadEspecializacion(
            @PathVariable String fileName,
            @PathVariable String fileType,
            @PathVariable Integer codCurso,
            @Autowired HttpServletResponse response
    ) {
        service.exportAprobadosEspecializacion(fileName, fileType, response, codCurso);
    }

    @PostMapping(value = "/generarMallaCurricular/{fileName}/{fileType}")
    public void downloadMalla(
            @PathVariable String fileName,
            @PathVariable String fileType,
            @Autowired HttpServletResponse response
    ) {
        service.exportMallaCurricular(fileName, fileType, response);
    }

    @PostMapping(value = "/reporteGeneral/{fileName}/{fileType}")
    public void downloadReporteGeneral(
            @PathVariable String fileName,
            @PathVariable String fileType,
            @Param("year") int year,
            @Autowired HttpServletResponse response
    ) {
        service.exporPeriodosAcademicosPeriodosProfesionalesCursosByYear(fileName, fileType, response, year);
    }

    @PostMapping(value = "/generarAntiguedades/{fileName}/{fileType}")
    public void downloadPrueba(
            @PathVariable String fileName,
            @PathVariable String fileType,
            @Autowired HttpServletResponse response
    ) throws Exception {
        service.exportAntiguedadesOperativos(fileName, fileType, response);
    }
    @PostMapping(value = "/generarEvaluacion/{codCurso}/{fileName}/{fileType}")
    public void downloadEvaluacionEspecializacion(
            @PathVariable String fileName,
            @PathVariable String fileType,
            @PathVariable Integer codCurso,
            @Autowired HttpServletResponse response
    ) throws Exception {
        service.exportReporteEncuestasESP(fileName, fileType, response, codCurso);
    }
    @PostMapping(value = "/generarNotas/{codEstudianteFor}/{codEstudianteEsp}/{fileName}/{fileType}")
    public void downloadEvaluacionEspecializacion(
            @PathVariable String fileName,
            @PathVariable String fileType,
            @PathVariable Integer codEstudianteFor,
            @PathVariable Integer codEstudianteEsp,
            @Autowired HttpServletResponse response
    ) throws Exception {
        service.exportReporteFichaPersonal(fileName, fileType, response, codEstudianteFor, codEstudianteEsp);
    }


}