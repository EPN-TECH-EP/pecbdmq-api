package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProCatalogoProyecto;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.profesionalizacion.ProCatalogoProyectoRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProCatalogoProyectoService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

@Service
public class ProCatalogoProyectoServiceImpl extends ProfesionalizacionServiceImpl<ProCatalogoProyecto, Integer, ProCatalogoProyectoRepository> implements ProCatalogoProyectoService {
    public ProCatalogoProyectoServiceImpl(ProCatalogoProyectoRepository repository) {
        super(repository);
    }

    @Override
    public Optional<ProCatalogoProyecto> findByNombreCatalogo(String nombreCatalogoProyecto) {
        return repository.findByNombreCatalogo(nombreCatalogoProyecto);
    }

    @Override
    public ProCatalogoProyecto save(ProCatalogoProyecto obj) throws DataException {
        Optional<ProCatalogoProyecto> proCatalogoProyecto = repository.findByNombreCatalogo(obj.getNombreCatalogo());
        if (proCatalogoProyecto.isPresent())
            throw new DataException(REGISTRO_YA_EXISTE);
        return super.save(obj);
    }

    @Override
    public ProCatalogoProyecto update(ProCatalogoProyecto datosGuardados) throws DataException {
        Optional<ProCatalogoProyecto> proCatalogoProyecto = repository.findByNombreCatalogo(datosGuardados.getNombreCatalogo());
        if (proCatalogoProyecto.isPresent() && !datosGuardados.getCodigo().equals(proCatalogoProyecto.get().getCodigo()))
            throw new DataException(REGISTRO_YA_EXISTE);
        return super.update(datosGuardados);
    }
}
