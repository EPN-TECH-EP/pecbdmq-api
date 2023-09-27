package epntech.cbdmq.pe.resource;

import epntech.cbdmq.pe.dominio.admin.Reporte;
import epntech.cbdmq.pe.servicio.ReporteService;
import epntech.cbdmq.pe.servicio.reporteria.ReporteServiceLocal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/reporte")
@RequiredArgsConstructor
public class ReporteResource {
    private final ReporteService service;
    private final ReporteServiceLocal reporteService;

    @GetMapping("/listar")
    public List<Reporte> listar(@RequestParam("modulo") String modulo) {
        return service.getByModulo(modulo);
    }

    @GetMapping(value = "/{codigo}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getReporte(@PathVariable("type") Long codigo) {
        byte[] data = service.getReportePDF(codigo);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping(value = "/generarReporteAprobadosReprobados/{fileName}/{fileType}")
    public void downloadFormacion(
            @PathVariable String fileName,
            @PathVariable String fileType,
            @Autowired HttpServletResponse response
    ) {
        reporteService.exportAprobadosFormacion(fileName, fileType, response);
    }

    @PostMapping(value = "/generarReporteAprobadosReprobados/{codCurso}/{fileName}/{fileType}")
    public void downloadEspecializacion(
            @PathVariable String fileName,
            @PathVariable String fileType,
            @PathVariable Integer codCurso,
            @Autowired HttpServletResponse response
    ) {
        reporteService.exportAprobadosEspecializacion(fileName, fileType, response, codCurso);
    }

    @PostMapping(value = "/generarMallaCurricular/{fileName}/{fileType}")
    public void downloadMalla(
            @PathVariable String fileName,
            @PathVariable String fileType,
            @Autowired HttpServletResponse response
    ) {
        reporteService.exportMallaCurricular(fileName, fileType, response);
    }

    @PostMapping(value = "/generarPrueba/{fileName}/{fileType}")
    public void downloadPrueba(
            @PathVariable String fileName,
            @PathVariable String fileType,
            @Autowired HttpServletResponse response
    ) {
        reporteService.exportarPrueba(fileName, fileType, response);
    }
}