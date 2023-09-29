package epntech.cbdmq.pe.resource;

import epntech.cbdmq.pe.dominio.admin.llamamiento.Funcionario;
import epntech.cbdmq.pe.servicio.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
