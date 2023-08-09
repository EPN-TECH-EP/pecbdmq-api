package epntech.cbdmq.pe.resource.especializacion;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.especializacion.Curso;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.especializacion.CursoDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;
import static epntech.cbdmq.pe.util.ResponseEntityUtil.response;

@RestController
@RequestMapping("/repositorioCurso")
public class CursoDocumentoResource {
    @Autowired
    private CursoDocumentoService cursoDocumentoService;
    @GetMapping("/getDocumentos/{id}")
    public ResponseEntity<?> getDocumentos(@PathVariable("id") Long codCurso) throws IOException {
        return new ResponseEntity<>(cursoDocumentoService.getDocumentos(codCurso), HttpStatus.OK);
    }
    @PutMapping("/updateDocumento/{id}")
    public ResponseEntity<?> updateDocumento(
            @PathVariable("id") Long codDocumento,
            @RequestParam(value = "archivo", required = true) MultipartFile archivo) throws IOException {
        return new ResponseEntity<>(cursoDocumentoService.updateDocumento(codDocumento, archivo), HttpStatus.OK);
    }

    @PostMapping("/uploadDocumentos")
    public ResponseEntity<Curso> uploadFiles(
            @RequestParam("codCursoEspecializacion") Long codCursoEspecializacion,
            @RequestParam("archivos") List<MultipartFile> archivos) throws IOException, ArchivoMuyGrandeExcepcion, DataException {
        return new ResponseEntity<>(cursoDocumentoService.uploadDocumentos(codCursoEspecializacion, archivos), HttpStatus.OK);
    }

    @DeleteMapping("/eliminarDocumento")
    public ResponseEntity<HttpResponse> eliminarArchivo(
            @RequestParam Long codCursoEspecializacion,
            @RequestParam Long codDocumento) throws IOException, DataException {
        cursoDocumentoService.deleteDocumento(codCursoEspecializacion, codDocumento);
        return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
    }
    @GetMapping("/documentosByCurso/{codigoCurso}")
    public Set<Documento> listarDocumentosByPeriodo(@PathVariable("codigoCurso") Long codigoCurso) {
        return cursoDocumentoService.getDocumentosByCurso(codigoCurso);
    }
    
}
