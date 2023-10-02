package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import epntech.cbdmq.pe.dominio.util.MateriaParaleloDocumento;
import epntech.cbdmq.pe.servicio.MateriaParaleloDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.DocumentoRuta;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

@RestController
@RequestMapping("/materiaParaleloDocumento")
public class MateriaParaleloDocumentoResource {


    @Autowired
    private MateriaParaleloDocumentoService objService;

    @GetMapping("/listar")
    public List<MateriaParaleloDocumento> listar() throws Exception {
        return objService.getAll();
    }


    @PostMapping("/guardarArchivo")
    public List<DocumentoRuta> guardarArchivo( @RequestParam Integer materia,@RequestParam Boolean esTarea, @RequestParam List<MultipartFile> archivo) throws Exception {
        List<DocumentoRuta> lista;
        lista = objService.guardarArchivo( materia,esTarea, archivo);
        return lista;
    }


    @DeleteMapping("/eliminarDocumento")
    public ResponseEntity<HttpResponse> eliminarArchivo(@RequestParam Integer codMateriaParaleloDocumento)
            throws IOException, DataException {

        objService.deleteDocumento(codMateriaParaleloDocumento);

        return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
