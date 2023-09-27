package epntech.cbdmq.pe.resource;

import epntech.cbdmq.pe.dominio.admin.Reporte;
import epntech.cbdmq.pe.servicio.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reporte")
@RequiredArgsConstructor
public class ReporteResource {

    private final ReporteService service;

	@GetMapping("/listar")
    public List<Reporte> listar(@RequestParam("modulo") String modulo) {
        return service.getByModulo(modulo);
    }

    @GetMapping(value = "/{codigo}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getReporte(@PathVariable("type") Long codigo) {
        byte[] data = service.getReportePDF(codigo);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
	
}
