package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.ResponseMessage.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Excel;
import epntech.cbdmq.pe.helper.ExcelHelper;
import epntech.cbdmq.pe.servicio.ExcelService;

@RestController
@RequestMapping("/excel")
public class ExcelResource {

  @Autowired
  ExcelService fileService;

  @PostMapping("/cargar")
  public ResponseEntity<?> uploadFile(@RequestParam("archivo") MultipartFile archivo) {

    if (ExcelHelper.hasExcelFormat(archivo)) {
      try {
        fileService.save(archivo);

        return response(HttpStatus.OK, CARGA_EXITOSA);
      } catch (Exception e) {
        return response(HttpStatus.EXPECTATION_FAILED, CARGA_NO_EXITOSA);
      }
    }

    return response(HttpStatus.BAD_REQUEST, CARGA_ARCHIVO_EXCEL);
  }

  @GetMapping("/listar")
  public ResponseEntity<List<Excel>> getAllDatos() {
    try {
      List<Excel> datos = fileService.getAllDatos();

      if (datos.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(datos, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/descargar")
  public ResponseEntity<Resource> getFile() {
    String filename = "datos.xlsx";
    InputStreamResource file = new InputStreamResource(fileService.load());

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
        .body(file);
  }

  private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
      return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
              message), httpStatus);
  }
}
