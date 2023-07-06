package epntech.cbdmq.pe.resource.formacion;

import epntech.cbdmq.pe.dominio.admin.MateriaParalelo;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.formacion.MateriaParaleloServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/materiaParalelo")
public class MateriaParaleloResource {
    @Autowired
    private MateriaParaleloServiceImpl objService;

    @GetMapping("/listar")
    public List<MateriaParalelo> listar() throws DataException {
        return objService.getMateriasParalelo();
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MateriaParalelo> guardar(@RequestBody MateriaParalelo obj) throws DataException {
        return new ResponseEntity<MateriaParalelo>(objService.saveMateriaParalelo(obj), HttpStatus.OK);
    }
}
