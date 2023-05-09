package epntech.cbdmq.pe.resource;

import epntech.cbdmq.pe.dominio.Usuario;
import epntech.cbdmq.pe.dominio.admin.Apelacion;
import epntech.cbdmq.pe.dominio.admin.Grado;
import epntech.cbdmq.pe.dominio.admin.Rango;
import epntech.cbdmq.pe.dominio.util.RangoDtoRead;
import epntech.cbdmq.pe.repositorio.admin.GradoRepository;
import epntech.cbdmq.pe.servicio.impl.GradoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/grado")
public class GradoResource {

    @Autowired
    private GradoServiceImpl service;

    @GetMapping("/listar")
    public List<Grado> listar() {
        return service.getAll();
    }

    @PostMapping("/listarRangos")
    public ResponseEntity<List<RangoDtoRead>> getCorreo(@RequestParam Integer codGrado) {
        List<RangoDtoRead> rango = service.getRangoByGrado(codGrado);
        return new ResponseEntity<>(rango, OK);
    }


}
