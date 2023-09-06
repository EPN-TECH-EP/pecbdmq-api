package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProCumpleRequisitos;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProInscripcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProCumpleRequisitosRepository;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProInscripcionRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProCumpleRequisitosService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

@Service
public class ProCumpleRequisitosServiceImpl extends ProfesionalizacionServiceImpl<ProCumpleRequisitos, Integer, ProCumpleRequisitosRepository> implements ProCumpleRequisitosService {

    private final ProInscripcionRepository inscripcionRepository;

    public ProCumpleRequisitosServiceImpl(ProCumpleRequisitosRepository repository, ProInscripcionRepository inscripcionRepository) {
        super(repository);
        this.inscripcionRepository = inscripcionRepository;
    }

    @Override
    public ProCumpleRequisitos save(ProCumpleRequisitos obj) throws DataException {
        Optional<ProCumpleRequisitos> objGuardado = repository.findByCodInscripcionAndCodRequisito(obj.getCodInscripcion(), obj.getCodRequisito());
        if (objGuardado.isPresent()) {

            ProCumpleRequisitos stp = objGuardado.get();
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
    public ProCumpleRequisitos update(ProCumpleRequisitos objActualizado) throws DataException {
        // TODO Auto-generated method stub

        Optional<ProCumpleRequisitos> objGuardado = repository.findByCodInscripcionAndCodRequisito(objActualizado.getCodInscripcion(), objActualizado.getCodRequisito());
        if (objGuardado.isPresent() && !objGuardado.get().getCodCumpleRequisito().equals(objActualizado.getCodCumpleRequisito())) {
            throw new DataException(REGISTRO_YA_EXISTE);
        }

        return super.update(objActualizado);
    }

    public boolean apruebaInscripcion(Integer codInspeccion) {
        try {
            ProInscripcion proInscripcion = inscripcionRepository.findById(codInspeccion).orElseGet(null);
            if (proInscripcion != null) {
                proInscripcion.setAceptado(true);
                inscripcionRepository.save(proInscripcion);
            }

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean rechazaInscripcion(Integer codInspeccion) {
        try {
            ProInscripcion proInscripcion = inscripcionRepository.findById(codInspeccion).orElseGet(null);
            if (proInscripcion != null) {
                proInscripcion.setAceptado(false);
                inscripcionRepository.save(proInscripcion);
            }

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
