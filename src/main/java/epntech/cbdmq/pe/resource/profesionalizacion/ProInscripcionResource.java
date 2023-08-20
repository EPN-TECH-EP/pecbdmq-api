package epntech.cbdmq.pe.resource.profesionalizacion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProInscripcion;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProCumpleRequisitosDto;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProInscripcionDto;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProInscripcionRepository;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ProInscripcionServiceImpl;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static epntech.cbdmq.pe.constante.MensajesConst.PA_ACTIVO;

@RestController
@RequestMapping("/proInscripcion")
public class ProInscripcionResource extends ProfesionalizacionResource<ProInscripcion, Integer, ProInscripcionRepository, ProInscripcionServiceImpl> {
    public ProInscripcionResource(ProInscripcionServiceImpl service) {
        super(service);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatos(@PathVariable("id") Integer codigo, @RequestBody ProInscripcion obj) throws DataException {
        return service.findById(codigo).map(datosGuardados -> {
            datosGuardados.setCodEstudiante(obj.getCodEstudiante());
            datosGuardados.setCodConvocatoria(obj.getCodConvocatoria());
            datosGuardados.setAdjunto(obj.getAdjunto());
            datosGuardados.setEstado(obj.getEstado());
            datosGuardados.setEmail(obj.getEmail());
            return super.actualizarDatos(datosGuardados);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/crearConDocumentos")
    public ResponseEntity<?> crear(@RequestParam("datosInscripcion") String datosInscripcion, @RequestParam("docsInscripcion") List<MultipartFile> docsInscripcion) throws IOException, ArchivoMuyGrandeExcepcion, MessagingException, ParseException, DataException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        JsonNode jsonNode = objectMapper.readTree(datosInscripcion);
        ProInscripcion proInscripcion = objectMapper.readValue(datosInscripcion, ProInscripcion.class);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String key = entry.getKey();
            JsonNode value = entry.getValue();
            if (key.equals("fechaInscripcion")) {
                Date fecha = dateFormat.parse(value.asText());
                proInscripcion.setFechaInscripcion(fecha);
            }
        }
        ProInscripcion proInscripcion1 = service.insertarInscripcionConDocumentos(proInscripcion, docsInscripcion);
        if (proInscripcion1 == null) {
            return response(HttpStatus.BAD_REQUEST, PA_ACTIVO);
        }
        return new ResponseEntity<>(proInscripcion1, HttpStatus.OK);
    }

    @GetMapping("/datos/{id}")
    public Optional<ProInscripcionDto> getProDatosInscripcion(@PathVariable("id") Integer codInscripcion) {
        return service.getProDatosInscripcion(codInscripcion);
    }

    @GetMapping("/datos/{id}/listar")
    public List<ProInscripcionDto> findByCodConvocatoria(@PathVariable("id") Integer codConvocatoria) {
        return service.findByCodConvocatoria(codConvocatoria);
    }

    @GetMapping("/datos/aceptados")
    public List<ProInscripcionDto> findByAceptado() {
        return service.findByAceptado();
    }

    @GetMapping("/requisitos/{id}")
    public List<ProCumpleRequisitosDto> findRequisitosByInscripcion(@PathVariable("id") Integer codInscripcion) {
        return service.findRequisitosByInscripcion(codInscripcion);
    }

}
