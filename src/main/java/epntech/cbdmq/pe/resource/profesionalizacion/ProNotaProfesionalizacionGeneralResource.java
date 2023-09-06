package epntech.cbdmq.pe.resource.profesionalizacion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProNotaProfesionalizacionGeneral;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProNotaProfesionalizacionGeneralRepository;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ProNotaProfesionalizacionGeneralServiceImpl;
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
@RequestMapping("/proNotaProfesionalizacionGeneral")
public class ProNotaProfesionalizacionGeneralResource extends ProfesionalizacionResource<ProNotaProfesionalizacionGeneral, Integer, ProNotaProfesionalizacionGeneralRepository, ProNotaProfesionalizacionGeneralServiceImpl>{
    public ProNotaProfesionalizacionGeneralResource(ProNotaProfesionalizacionGeneralServiceImpl service) {
        super(service);
    }

    @PostMapping("/crearcondocumentos")
    public ResponseEntity<?> crear(@RequestParam("datosNotas") String datosNotas, @RequestParam("docsNotas") List<MultipartFile> docsNotas) throws IOException, ArchivoMuyGrandeExcepcion, MessagingException, ParseException, DataException{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        JsonNode jsonNode = objectMapper.readTree(datosNotas);
        ProNotaProfesionalizacionGeneral proInscripcion = objectMapper.readValue(datosNotas, ProNotaProfesionalizacionGeneral.class);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String key = entry.getKey();
            JsonNode value = entry.getValue();
            if (key.equals("fecha")) {
                Date fecha = dateFormat.parse(value.asText());
                proInscripcion.setFecha(fecha);
            }
        }
        ProNotaProfesionalizacionGeneral proInscripcion1 = service.insertarNotasConDocumentos(proInscripcion, docsNotas);
        if (proInscripcion1 == null) {
            return response(HttpStatus.BAD_REQUEST, PA_ACTIVO);
        }
        return new ResponseEntity<>(proInscripcion1, HttpStatus.OK);
    }

    @GetMapping("/{id}/paralelo")
    public Optional<ProNotaProfesionalizacionGeneral> getByMateriaparalelo(@PathVariable("id") Integer id){
        return service.getByMateriaparalelo(id);
    }
}
