package epntech.cbdmq.pe.resource.fichaPersonal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.formacion.EstudianteMateriaDocumento;
import epntech.cbdmq.pe.dominio.fichaPersonal.especializacion.CursoDocumentoEstudiante;
import epntech.cbdmq.pe.dominio.fichaPersonal.formacion.EstudianteMateriaDocumentoDto;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.fichaPersonal.CursoDocumentoEstudianteService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;
import static epntech.cbdmq.pe.util.ResponseEntityUtil.response;

@RestController
@RequestMapping("/estudianteCursoDocumento")
public class EstudianteCursoDocumentoResource {
    @Autowired
    CursoDocumentoEstudianteService cursoDocumentoEstudianteSvc;

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> guardar(@RequestBody CursoDocumentoEstudiante obj) {
        return new ResponseEntity<>(cursoDocumentoEstudianteSvc.saveOrUpdate(obj), HttpStatus.OK);
    }

    @PostMapping("/crearFully")
    public ResponseEntity<?> crear(@RequestParam("datosEstudianteMateriaDocumento") String datosEstudianteMateriaDocumento, @RequestParam("docs") List<MultipartFile> docsPeriodoAcademico) throws IOException, ArchivoMuyGrandeExcepcion, MessagingException, ParseException, DataException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        CursoDocumentoEstudiante convocatoria = objectMapper.readValue(datosEstudianteMateriaDocumento, CursoDocumentoEstudiante.class);
        CursoDocumentoEstudiante pa = cursoDocumentoEstudianteSvc.saveConArchivo(convocatoria, docsPeriodoAcademico);

        if (pa == null)
            return response(HttpStatus.BAD_REQUEST, "No se pudo subir el archivo");

        return new ResponseEntity<>(pa, HttpStatus.OK);
    }

    @GetMapping("/listar")
    public List<CursoDocumentoEstudiante> listar() {
        return cursoDocumentoEstudianteSvc.getAll();
    }

    @GetMapping("/listar/documentos/estudiante/{idEstudiante}/curso/{idCurso}")
    public List<CursoDocumentoEstudiante> listarByEstudianteMateriaParalelo(@PathVariable("idEstudiante") Integer idEstudiante, @PathVariable("idCurso") Integer idCurso) {
        return cursoDocumentoEstudianteSvc.getAllByEstudianteAndCurso(idEstudiante, idCurso);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDocumentoEstudiante> obtenerPorId(@PathVariable("id") int codigo) {
        return new ResponseEntity<>(cursoDocumentoEstudianteSvc.getEstudianteCursoDocumentoById(codigo).get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") int codigo) {
        cursoDocumentoEstudianteSvc.deleteEstudianteMateriaDocumento(codigo);
        return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
    }
}
