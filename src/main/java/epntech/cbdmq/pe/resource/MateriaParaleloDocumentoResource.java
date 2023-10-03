package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_ELIMINADO_EXITO;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.formacion.MateriaCursoDocumentoDto;
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
    @GetMapping("/listarByCodMateriaParalelo/{id}")
    public List<MateriaCursoDocumentoDto> listarMateriaDocumentoDtoByCodMateria(@PathVariable("id") Integer codMateriaParalelo) throws Exception {
        return objService.getAllByCodMateriaParalelo(codMateriaParalelo);
    }
    @GetMapping("/listarDocumentos/{id}")
    public Set<Documento> listarDocumentosByCodMateria(@PathVariable("id") Integer codMateriaParalelo) throws Exception {
        return objService.getDocumentosByMateriaParalelo(codMateriaParalelo.longValue());
    }
    @GetMapping("/listarTareas/{id}")
    public Set<Documento>  listarTareasByCodMateria(@PathVariable("id") Integer codMateriaParalelo) throws Exception {
        return objService.getTareasByMateriaParalelo(codMateriaParalelo.longValue());
    }


    @PostMapping("/guardarArchivo")
    public List<DocumentoRuta> guardarArchivo( @RequestParam Integer materia,@RequestParam Boolean esTarea, @RequestParam List<MultipartFile> archivo,@RequestParam String descripcion) throws Exception {
        List<DocumentoRuta> lista;
        lista = objService.guardarArchivo( materia,esTarea, archivo, descripcion);
        return lista;
    }


    @DeleteMapping("/eliminarDocumento")
    public ResponseEntity<HttpResponse> eliminarArchivo(@RequestParam Integer codMateriaParalelo, @RequestParam Integer codDocumento)
            throws IOException, DataException {

        objService.deleteDocumentoI(codMateriaParalelo.longValue(),codDocumento.longValue());

        return response(HttpStatus.OK, REGISTRO_ELIMINADO_EXITO);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }
}
