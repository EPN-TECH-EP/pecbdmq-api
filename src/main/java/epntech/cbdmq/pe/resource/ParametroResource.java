package epntech.cbdmq.pe.resource;

import epntech.cbdmq.pe.dominio.Parametro;
import epntech.cbdmq.pe.repositorio.ParametroRepository;
import epntech.cbdmq.pe.resource.profesionalizacion.ProfesionalizacionResource;
import epntech.cbdmq.pe.servicio.impl.profesionalizacion.ParametroServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parametro")
public class ParametroResource extends ProfesionalizacionResource<Parametro, Long, ParametroRepository, ParametroServiceImpl> {
    public ParametroResource(ParametroServiceImpl service) {
        super(service);
    }
}
