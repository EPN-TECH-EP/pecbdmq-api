package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProParalelo;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProParaleloRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProParaleloService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

@Service
public class ProParaleloServiceImpl extends ProfesionalizacionServiceImpl<ProParalelo, Integer, ProParaleloRepository> implements ProParaleloService {
    public ProParaleloServiceImpl(ProParaleloRepository repository){
        super(repository);
    }
    @Override
    public ProParalelo save(ProParalelo obj) throws DataException {
        // TODO Auto-generated method stub
        if(obj.getNombreParalelo().trim().isEmpty())
            throw new DataException(REGISTRO_VACIO);
        Optional<ProParalelo> objGuardado = repository.findByNombreParaleloIgnoreCase(obj.getNombreParalelo());
        if (objGuardado.isPresent()) {
            // valida si existe eliminado
            ProParalelo stp = objGuardado.get();
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
    public ProParalelo update(ProParalelo objActualizado) throws DataException {
        // TODO Auto-generated method stub
        if(objActualizado.getNombreParalelo().trim().isEmpty())
            throw new DataException(REGISTRO_VACIO);
        Optional<ProParalelo> objGuardado = repository.findByNombreParaleloIgnoreCase(objActualizado.getNombreParalelo());
        if (objGuardado.isPresent()&& !objGuardado.get().getCodParalelo().equals(objActualizado.getCodParalelo())) {
            throw new DataException(REGISTRO_YA_EXISTE);
        }
        return super.update(objActualizado);
    }
}
