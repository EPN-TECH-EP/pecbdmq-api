package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProDelegados;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProDelegadoDatosDto;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.repository.ProDelegadoDatosRepository;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProDelegadosRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProDelegadosService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;
@Service
public class ProDelegadosServiceImpl extends ProfesionalizacionServiceImpl<ProDelegados, Integer, ProDelegadosRepository> implements ProDelegadosService {

    private final ProDelegadoDatosRepository proDelegadoDatosRepository;
    public ProDelegadosServiceImpl(ProDelegadosRepository proDelegadosRepository, ProDelegadoDatosRepository proDelegadoDatosRepository) {
        super(proDelegadosRepository);
        this.proDelegadoDatosRepository = proDelegadoDatosRepository;
    }

    @Override
    public ProDelegados save(ProDelegados obj) throws DataException {
        Optional<ProDelegados> objGuardado = repository.findByCodUsuario(obj.getCodUsuario());
        if (objGuardado.isPresent()) {
            ProDelegados stp = objGuardado.get();
            if (stp.getEstado().compareToIgnoreCase(EstadosConst.ELIMINADO) == 0) {
                stp.setEstado(EstadosConst.ACTIVO);
                return super.save(stp);
            } else {
                throw new DataException(REGISTRO_YA_EXISTE);
            }
        }
        obj.setEstado(EstadosConst.ACTIVO);
        return super.save(obj);
    }

    @Override
    public ProDelegados update(ProDelegados objActualizado) throws DataException {
        Optional<ProDelegados> objGuardado = repository.findByCodUsuario(objActualizado.getCodUsuario());
        if (objGuardado.isPresent()&& !objGuardado.get().getCodDelegado().equals(objActualizado.getCodDelegado())) {
            throw new DataException(REGISTRO_YA_EXISTE);
        }

        return super.update(objActualizado);
    }

    public List<ProDelegadoDatosDto> getAllAssigned() {
        return this.proDelegadoDatosRepository.getAllAssigned();
    }
}
