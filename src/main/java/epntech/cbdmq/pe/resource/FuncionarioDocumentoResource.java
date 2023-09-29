package epntech.cbdmq.pe.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.llamamiento.FuncionarioDocumento;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.FuncionarioDocumentoService;
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
@RequestMapping("/funcionarioDocumento")
public class FuncionarioDocumentoResource {
    @Autowired
    private FuncionarioDocumentoService funcionarioDocumentoSvc;

    @GetMapping("/listarSancionesByFuncionario/{codfuncionario}")
    public List<FuncionarioDocumento> listarSancionesByFuncionario(@PathVariable("codfuncionario") Integer codFuncionario) {
        return funcionarioDocumentoSvc.getByFuncionarioSancion(codFuncionario);
    }

    @GetMapping("/listarReconocimientosByFuncionario/{codfuncionario}")
    public List<FuncionarioDocumento> listarReconocimientosByFuncionario(@PathVariable("codfuncionario") Integer codFuncionario) {
        return funcionarioDocumentoSvc.getByFuncionarioReconocimiento(codFuncionario);
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> guardar(@RequestBody FuncionarioDocumento obj) {
        return new ResponseEntity<>(funcionarioDocumentoSvc.saveOrUpdate(obj), HttpStatus.OK);
    }

    @PostMapping("/crearFully")
    public ResponseEntity<?> crear(@RequestParam("datosFuncionarioDocumento") String datosFuncionarioDocumento, @RequestParam("docs") List<MultipartFile> docsPeriodoAcademico) throws IOException, ArchivoMuyGrandeExcepcion, MessagingException, ParseException, DataException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        FuncionarioDocumento convocatoria = objectMapper.readValue(datosFuncionarioDocumento, FuncionarioDocumento.class);
        FuncionarioDocumento pa = funcionarioDocumentoSvc.saveConArchivo(convocatoria, docsPeriodoAcademico);

        if (pa == null)
            return response(HttpStatus.BAD_REQUEST, "No se pudo subir el archivo");

        return new ResponseEntity<>(pa, HttpStatus.OK);
    }

    @GetMapping("/listar")
    public List<FuncionarioDocumento> listar() {
        return funcionarioDocumentoSvc.getAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDocumento> obtenerPorId(@PathVariable("id") int codigo) {
        return new ResponseEntity<>(funcionarioDocumentoSvc.getById(codigo).get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponse> eliminarDatos(@PathVariable("id") int codigo) {
        funcionarioDocumentoSvc.deleteFuncionarioDocumento(codigo);
        return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
    }
}

