package epntech.cbdmq.pe.resource;

import epntech.cbdmq.pe.dominio.admin.llamamiento.Funcionario;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static epntech.cbdmq.pe.util.ResponseEntityUtil.response;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioResource {
    @Autowired
    private FuncionarioService funcionarioSvc;
    @GetMapping("/listarTodos")
    public List<Funcionario> listar(){
        return funcionarioSvc.listAll();
    }
    @PostMapping("/crear")
    public Funcionario crear(@RequestBody Funcionario funcionario) throws DataException {
        return funcionarioSvc.save(funcionario);
    }
    @PostMapping("/enviarNotificacionProspecto")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> enviarNotificacionOperativo(@Param("limiteProspectos") int limiteProspectos) throws Exception {

        try {
            funcionarioSvc.notificarMejoresProspectos(limiteProspectos);
            return response(OK, "Notificacion enviada a cada uno de los mejores prospectos");

        } catch (Exception ex) {

            return response(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> getDataOperativos() throws Exception {

        try {
            return new ResponseEntity<>(funcionarioSvc.servicioOperativos(), HttpStatus.OK);
        } catch (Exception ex) {

            return response(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
    @GetMapping("/operativosOrderByAntiguedad")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> getDataOperativosOrderByAntiguedad() throws Exception {

        try {
            return new ResponseEntity<>(funcionarioSvc.servicioOperativosOrderByAntiguedad(), HttpStatus.OK);
        } catch (Exception ex) {

            return response(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

}
