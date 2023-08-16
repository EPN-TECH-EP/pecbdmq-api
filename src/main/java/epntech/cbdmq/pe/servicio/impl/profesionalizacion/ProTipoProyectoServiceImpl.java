package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProTipoProyecto;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.profesionalizacion.ProTipoProyectoRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProTipoProyectoService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

@Service
public class ProTipoProyectoServiceImpl extends ProfesionalizacionServiceImpl<ProTipoProyecto, Integer, ProTipoProyectoRepository> implements ProTipoProyectoService {

    public ProTipoProyectoServiceImpl(ProTipoProyectoRepository repository){
        super(repository);
    }
    @Override
    public Optional<ProTipoProyecto> findByNombre(String nombreTipo) {
        return repository.findByNombreTipo(nombreTipo);

    }
    @Override
    public ProTipoProyecto save(ProTipoProyecto obj) throws DataException{
        Optional<ProTipoProyecto> proTipoProyecto = repository.findByNombreTipo(obj.getNombreTipo());
        if (proTipoProyecto.isPresent())
            throw new DataException(REGISTRO_YA_EXISTE);
        return super.save(obj);
    }
    @Override
    public ProTipoProyecto update(ProTipoProyecto datosGuardados) throws DataException {
        Optional<ProTipoProyecto> proTipoProyecto = repository.findByNombreTipo(datosGuardados.getNombreTipo());
        if (proTipoProyecto.isPresent() && !datosGuardados.getCodigo().equals(proTipoProyecto.get().getCodigo()))
            throw new DataException(REGISTRO_YA_EXISTE);
        return super.update(datosGuardados);
    }
}
