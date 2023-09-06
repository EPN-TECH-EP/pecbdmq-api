package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProPeriodoSemestre;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProPeriodoSemestreDto;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.repository.ProPeriodoSemestreDatosRepository;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProPeriodoSemestreRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProPeriodoSemestreService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

@Service
public class ProPeriodoSemestreServiceImpl extends ProfesionalizacionServiceImpl<ProPeriodoSemestre, Integer, ProPeriodoSemestreRepository> implements ProPeriodoSemestreService {

    private final ProPeriodoSemestreDatosRepository datosRepository;
    public ProPeriodoSemestreServiceImpl(ProPeriodoSemestreRepository repository, ProPeriodoSemestreDatosRepository datosRepository) {
        super(repository);
        this.datosRepository = datosRepository;
    }

    @Override
    public ProPeriodoSemestre save(ProPeriodoSemestre obj) throws DataException {
        Optional<ProPeriodoSemestre> objGuardado = repository.findByCodPeriodoAndCodSemestre(obj.getCodPeriodo(), obj.getCodSemestre());
        if (objGuardado.isPresent()) {
            ProPeriodoSemestre stp = objGuardado.get();
            if (stp.getEstado().compareToIgnoreCase(EstadosConst.ELIMINADO) == 0) {
                stp.setEstado(EstadosConst.ACTIVO);
                return super.save(stp);
            } else {
                throw new DataException(REGISTRO_YA_EXISTE);
            }
        }
        return super.save(obj);
    }

    @Override
    public ProPeriodoSemestre update(ProPeriodoSemestre objActualizado) throws DataException {
        // TODO Auto-generated method stub

        Optional<ProPeriodoSemestre> objGuardado = repository.findByCodPeriodoAndCodSemestre(objActualizado.getCodPeriodo(), objActualizado.getCodSemestre());
        if (objGuardado.isPresent()&& !objGuardado.get().getCodPeriodoSemestre().equals(objActualizado.getCodPeriodoSemestre())) {
            throw new DataException(REGISTRO_YA_EXISTE);
        }

        return super.update(objActualizado);
    }

    public List<ProPeriodoSemestreDto> getAllByPeriodo(Integer codigo) {
        return datosRepository.getAllByCodPeriodo(codigo);
    }
}
