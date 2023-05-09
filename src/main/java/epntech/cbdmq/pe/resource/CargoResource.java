package epntech.cbdmq.pe.resource;

import epntech.cbdmq.pe.dominio.admin.Cargo;
import epntech.cbdmq.pe.dominio.admin.Grado;
import epntech.cbdmq.pe.repositorio.admin.GradoRepository;
import epntech.cbdmq.pe.servicio.impl.CargoServiceImpl;
import epntech.cbdmq.pe.servicio.impl.GradoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cargo")
public class CargoResource {
    @Autowired
    private CargoServiceImpl service;

    @GetMapping("/listar")
    public List<Cargo> listar() {
        return service.getAll();
    }
}
