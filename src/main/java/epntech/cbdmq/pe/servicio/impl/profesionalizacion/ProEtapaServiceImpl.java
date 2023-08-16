package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProEtapas;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProEtapasRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProEtapaService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

@Service
public class ProEtapaServiceImpl extends ProfesionalizacionServiceImpl<ProEtapas, Integer, ProEtapasRepository> implements ProEtapaService {

    public ProEtapaServiceImpl(ProEtapasRepository repository){
        super(repository);
    }
    @Override
    public ProEtapas save(ProEtapas obj) throws DataException {

        if(obj.getNombreEtapa().trim().isEmpty())
            throw new DataException(REGISTRO_VACIO);
        Optional<ProEtapas> objGuardado = repository.findByNombreEtapaIgnoreCase(obj.getNombreEtapa());
        if (objGuardado.isPresent()) {

            ProEtapas stp = objGuardado.get();
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
    public ProEtapas update(ProEtapas objActualizado) throws DataException {
        // TODO Auto-generated method stub
        if(objActualizado.getNombreEtapa().trim().isEmpty())
            throw new DataException(REGISTRO_VACIO);
        Optional<ProEtapas> objGuardado = repository.findByNombreEtapaIgnoreCase(objActualizado.getNombreEtapa());
        if (objGuardado.isPresent()&& !objGuardado.get().getCodEtapa().equals(objActualizado.getCodEtapa())) {
            throw new DataException(REGISTRO_YA_EXISTE);
        }

        return super.update(objActualizado);
    }
}
