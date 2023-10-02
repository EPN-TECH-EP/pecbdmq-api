package epntech.cbdmq.pe.resource;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.DocumentoRuta;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.DocumentoPruebaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

@RestController
@RequestMapping("/documentoPrueba")
public class DocumentoPruebaResource {
    @Autowired
    private DocumentoPruebaServiceImpl objService;


    @PostMapping("/guardarArchivo")
    public List<DocumentoRuta> guardarArchivo(@RequestParam Integer pruebaDetalle, @RequestParam List<MultipartFile> archivo) throws Exception {
        List<DocumentoRuta>lista= new ArrayList<>();
        lista= objService.guardarArchivo(pruebaDetalle, archivo);
        return lista;
    }


    @DeleteMapping("/eliminarDocumento")
    public ResponseEntity<HttpResponse> eliminarArchivo(@RequestParam Integer pruebaDetalle, @RequestParam Long codDocumento)
            throws IOException, DataException {

        objService.deleteDocumento(pruebaDetalle,codDocumento);

        return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
    }
    @GetMapping("/listar/{id}")
    public Set<Documento> listarDocumentos(@PathVariable("id") Integer id) {
        return objService.getDocumentos(id);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }

}
