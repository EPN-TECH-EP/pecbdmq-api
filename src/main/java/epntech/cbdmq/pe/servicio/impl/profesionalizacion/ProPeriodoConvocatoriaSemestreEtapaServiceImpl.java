package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProPeriodoConvocatoriaSemestreEtapa;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProPeriodoConvocatoriaSemestreEtapaRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProPeriodoConvocatoriaSemestreEtapaService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

@Service
public class ProPeriodoConvocatoriaSemestreEtapaServiceImpl extends ProfesionalizacionServiceImpl<ProPeriodoConvocatoriaSemestreEtapa, Integer, ProPeriodoConvocatoriaSemestreEtapaRepository> implements ProPeriodoConvocatoriaSemestreEtapaService {
    public ProPeriodoConvocatoriaSemestreEtapaServiceImpl(ProPeriodoConvocatoriaSemestreEtapaRepository repository){
        super(repository);
    }
    @Override
    public ProPeriodoConvocatoriaSemestreEtapa save(ProPeriodoConvocatoriaSemestreEtapa obj) throws DataException {
        Optional<ProPeriodoConvocatoriaSemestreEtapa> objGuardado = repository.findByCodPeriodo(obj.getCodPeriodo());
        if (objGuardado.isPresent()) {
            ProPeriodoConvocatoriaSemestreEtapa stp = objGuardado.get();
            if (stp.getEstado().compareToIgnoreCase(EstadosConst.ELIMINADO) == 0) {
                stp.setEstado(EstadosConst.ACTIVO);
                return repository.save(stp);
            } else {
                throw new DataException(REGISTRO_YA_EXISTE);
            }
        }
        return super.save(obj);
    }

    @Override
    public ProPeriodoConvocatoriaSemestreEtapa update(ProPeriodoConvocatoriaSemestreEtapa objActualizado) throws DataException {
        Optional<ProPeriodoConvocatoriaSemestreEtapa> objGuardado = repository.findByCodPeriodo(objActualizado.getCodPeriodo());
        if (objGuardado.isPresent()&& !objGuardado.get().getCodPeriodoConvocatoriaSemestreEtapa().equals(objActualizado.getCodPeriodoConvocatoriaSemestreEtapa())) {
            throw new DataException(REGISTRO_YA_EXISTE);
        }
        return repository.save(objActualizado);
    }

}
