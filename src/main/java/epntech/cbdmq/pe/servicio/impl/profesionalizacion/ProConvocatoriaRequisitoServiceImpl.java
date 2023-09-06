package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProConvocatoriaRequisito;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProConvocatoriaRequisitoDto;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.repository.ProConvocatoriaRequisitosDatosRepository;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.profesionalizacion.ProConvocatoriaRequisitoRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProConvocatoriaRequisitoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

@Service
public class ProConvocatoriaRequisitoServiceImpl extends ProfesionalizacionServiceImpl<ProConvocatoriaRequisito, Integer, ProConvocatoriaRequisitoRepository> implements ProConvocatoriaRequisitoService {

    private final ProConvocatoriaRequisitosDatosRepository datosRepository;
    public ProConvocatoriaRequisitoServiceImpl(ProConvocatoriaRequisitoRepository repository, ProConvocatoriaRequisitosDatosRepository datosRepository) {
        super(repository);
        this.datosRepository = datosRepository;
    }

    @Override
    public Optional<ProConvocatoriaRequisito> findByConvocatoriaAndRequisito(Integer codigoConvocatoria, Integer codigoRequisito) {
        return repository.findByCodigoConvocatoriaAndCodigoRequisito(codigoConvocatoria,codigoRequisito);
    }

    @Override
    public ProConvocatoriaRequisito save(ProConvocatoriaRequisito obj) throws DataException {
        Optional<ProConvocatoriaRequisito> proConvocatoria = repository.findByCodigoConvocatoriaAndCodigoRequisito(obj.getCodigoConvocatoria(), obj.getCodigoRequisito());
        if (proConvocatoria.isPresent())
            throw new DataException(REGISTRO_YA_EXISTE);
        return super.save(obj);
    }

    @Override
    public ProConvocatoriaRequisito update(ProConvocatoriaRequisito datosGuardados) throws DataException {
        Optional<ProConvocatoriaRequisito> proConvocatoria = repository.findByCodigoConvocatoriaAndCodigoRequisito(datosGuardados.getCodigoConvocatoria(), datosGuardados.getCodigoRequisito());
        if (proConvocatoria.isPresent() && !datosGuardados.getCodigo().equals(proConvocatoria.get().getCodigo()))
            throw new DataException(REGISTRO_YA_EXISTE);
        return super.update(datosGuardados);
    }

    public List<ProConvocatoriaRequisitoDto> findRequisitosByConvocatoria(Integer codConvocatoria) {
        return datosRepository.findByProConvocatoria(codConvocatoria);
    }
}
