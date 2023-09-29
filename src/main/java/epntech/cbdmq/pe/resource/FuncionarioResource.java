package epntech.cbdmq.pe.resource;

import epntech.cbdmq.pe.dominio.admin.llamamiento.Funcionario;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioResource {
    @Autowired
    private FuncionarioService funcionarioSvc;
    @GetMapping("/listar")
    public List<Funcionario> listar(){
        return funcionarioSvc.listAll();
    }
    @PostMapping("/crear")
    public Funcionario crear(@RequestBody Funcionario funcionario) throws DataException {
        return funcionarioSvc.save(funcionario);
    }

}
