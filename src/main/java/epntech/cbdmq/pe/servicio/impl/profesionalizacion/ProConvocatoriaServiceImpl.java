package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProConvocatoria;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.profesionalizacion.ProConvocatoriaRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProConvocatoriaService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

@Service
public class ProConvocatoriaServiceImpl extends ProfesionalizacionServiceImpl<ProConvocatoria, Integer, ProConvocatoriaRepository> implements ProConvocatoriaService {

    public ProConvocatoriaServiceImpl(ProConvocatoriaRepository repository) {
        super(repository);
    }

    @Override
    public Optional<ProConvocatoria> getByCodigoUnicoConvocatoria(String codigoUnicoConvocatoria) {
        return repository.findByCodigoUnicoConvocatoria(codigoUnicoConvocatoria);
    }

    @Override
    public ProConvocatoria save(ProConvocatoria obj) throws DataException {
        Optional<ProConvocatoria> proConvocatoria = repository.findByCodigoUnicoConvocatoria(obj.getCodigoUnicoConvocatoria());
        if (proConvocatoria.isPresent())
            throw new DataException(REGISTRO_YA_EXISTE);
        proConvocatoria = repository.findByCodPeriodo(obj.getCodPeriodo());
        if (proConvocatoria.isPresent())
            throw new DataException(CONVOCATORIA_YA_EXISTE);
        return super.save(obj);
    }

    @Override
    public ProConvocatoria update(ProConvocatoria datosGuardados) throws DataException {
        Optional<ProConvocatoria> proConvocatoria = repository.findByCodigoUnicoConvocatoria(datosGuardados.getCodigoUnicoConvocatoria());
        if (proConvocatoria.isPresent() && !Objects.equals(proConvocatoria.get().getCodigo(), datosGuardados.getCodigo()))
            throw new DataException(REGISTRO_YA_EXISTE);
        return super.update(datosGuardados);
    }

    @Override
    public String getCodConvocatoriaCreacion() {
        return repository.findNextLastCodigo();
    }

    public Optional<ProConvocatoria> getByPeriodo(Integer id) {
        return repository.findByCodPeriodo(id);
    }

    public ProConvocatoria updateEstadoConvocatoria(Integer id, String estado) throws DataException {
        Optional<ProConvocatoria> byId = repository.findById(id);
        if (byId.isPresent()) {
            if (estado.equals("INSCRIPCION")) {
                Optional<ProConvocatoria> notificacion = repository.findByEstado("INSCRIPCION");
                if (notificacion.isPresent()) {
                    throw new DataException("Ya existe una convocatoria en estado INSCRIPCIÓN.");
                }
            }
            ProConvocatoria proConvocatoria = byId.get();
            proConvocatoria.setEstado(estado);
            return repository.save(proConvocatoria);
        } else {
            throw new DataException(REGISTRO_NO_EXISTE);
        }
    }
}
