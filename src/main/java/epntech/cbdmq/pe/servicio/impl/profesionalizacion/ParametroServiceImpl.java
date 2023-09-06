package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.dominio.Parametro;
import epntech.cbdmq.pe.repositorio.ParametroRepository;
import org.springframework.stereotype.Service;

@Service
public class ParametroServiceImpl extends ProfesionalizacionServiceImpl<Parametro, Long, ParametroRepository>{
    public ParametroServiceImpl(ParametroRepository repository) {
        super(repository);
    }
}
