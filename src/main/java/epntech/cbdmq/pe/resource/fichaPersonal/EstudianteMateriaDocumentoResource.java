package epntech.cbdmq.pe.resource.fichaPersonal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.ConvocatoriaFor;
import epntech.cbdmq.pe.dominio.admin.formacion.EstudianteMateriaDocumento;
import epntech.cbdmq.pe.dominio.util.PeriodoAcademicoFor;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.fichaPersonal.EstudianteMateriaDocumentoService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import static epntech.cbdmq.pe.constante.MensajesConst.PA_ACTIVO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;
import static epntech.cbdmq.pe.util.ResponseEntityUtil.response;

@RestController
@RequestMapping("/estudianteMateriaDocumento")
public class EstudianteMateriaDocumentoResource {

    @Autowired
    EstudianteMateriaDocumentoService estudianteMateriaDocumentoSvc;

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> guardar(@RequestBody EstudianteMateriaDocumento obj) {
        return new ResponseEntity<>(estudianteMateriaDocumentoSvc.saveOrUpdate(obj), HttpStatus.OK);
    }

    @PostMapping("/crearFully")
    public ResponseEntity<?> crear(@RequestParam("datosEstudianteMateriaDocumento") String datosEstudianteMateriaDocumento, @RequestParam("docs") List<MultipartFile> docsPeriodoAcademico) throws IOException, ArchivoMuyGrandeExcepcion, MessagingException, ParseException, DataException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        EstudianteMateriaDocumento convocatoria = objectMapper.readValue(datosEstudianteMateriaDocumento, EstudianteMateriaDocumento.class);
        EstudianteMateriaDocumento pa = estudianteMateriaDocumentoSvc.saveConArchivo(convocatoria, docsPeriodoAcademico);

        if (pa == null)
            return response(HttpStatus.BAD_REQUEST, "No se pudo subir el archivo");

        return new ResponseEntity<>(pa, HttpStatus.OK);
    }

    @GetMapping("/listar")
    public List<EstudianteMateriaDocumento> listar() {
        return estudianteMateriaDocumentoSvc.getAll();
    }

    @GetMapping("/listar/documentos/estudiante/{idEstudiante}/materia/{idMateria}")
    public List<EstudianteMateriaDocumento> listarByEstudianteMateriaParalelo(@PathVariable("idEstudiante") Integer idEstudiante, @PathVariable("idMateria") Integer idMateria) {
        return estudianteMateriaDocumentoSvc.getAllByEstudianteAndMateriaParalelo(idEstudiante, idMateria);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteMateriaDocumento> obtenerPorId(@PathVariable("id") int codigo) {
        return new ResponseEntity<>(estudianteMateriaDocumentoSvc.getEstudianteMateriaDocumentoById(codigo).get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") int codigo) {
        estudianteMateriaDocumentoSvc.deleteEstudianteMateriaDocumento(codigo);
        return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
    }


}
