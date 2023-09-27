package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProPeriodos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.profesionalizacion.ProConvocatoriaRepository;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProPeriodosRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProPeriodoService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

@Service
public class ProPeriodoServiceImpl extends ProfesionalizacionServiceImpl<ProPeriodos, Integer, ProPeriodosRepository> implements ProPeriodoService {
    private final ProConvocatoriaRepository convocatoriaRepository;

    public ProPeriodoServiceImpl(ProPeriodosRepository repository, ProConvocatoriaRepository convocatoriaRepository) {
        super(repository);
        this.convocatoriaRepository = convocatoriaRepository;
    }

    @Override
    public ProPeriodos save(ProPeriodos obj) throws DataException {
        if (obj.getNombrePeriodo().trim().isEmpty())
            throw new DataException(REGISTRO_VACIO);
        Optional<ProPeriodos> objGuardado = repository.findByNombrePeriodoIgnoreCase(obj.getNombrePeriodo());
        if (objGuardado.isPresent()) {
            ProPeriodos stp = objGuardado.get();
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
    public ProPeriodos update(ProPeriodos objActualizado) throws DataException {
        // TODO Auto-generated method stub
        if (objActualizado.getNombrePeriodo().trim().isEmpty())
            throw new DataException(REGISTRO_VACIO);
        Optional<ProPeriodos> objGuardado = repository.findByNombrePeriodoIgnoreCase(objActualizado.getNombrePeriodo());
        if (objGuardado.isPresent() && !objGuardado.get().getCodigoPeriodo().equals(objActualizado.getCodigoPeriodo())) {
            throw new DataException(REGISTRO_YA_EXISTE);
        }
        return super.update(objActualizado);
    }


    @Override
    public List<ProPeriodos> findByEstado(String estado) {
        return repository.findByEstado(estado);
    }

    @Override
    public List<ProPeriodos> findByFechaInicioBetween(Date startDate, Date endDate) {
        return repository.findByFechaInicioBetween(startDate, endDate);
    }

    @Override
    public void delete(Integer codigo) throws DataException {
        var convocatoria = convocatoriaRepository.findByCodPeriodo(codigo);
        if (convocatoria.isPresent()) {
            throw new DataException("El Cohorte se encuentra vinculado a una convocatoria");
        }
        super.delete(codigo);
    }
}
