package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProSemestreMateriaParaleloInstructor;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProParaleloInstructorDto;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.repository.ProParaleloInstructorDatosRepository;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProEstudianteSemestreMateriaParaleloInstructorRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProSemestreMateriaParaleloInstructorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

@Service
public class ProSemestreMateriaParaleloInstructorImpl extends ProfesionalizacionServiceImpl<ProSemestreMateriaParaleloInstructor, Integer, ProEstudianteSemestreMateriaParaleloInstructorRepository> implements ProSemestreMateriaParaleloInstructorService {

    private final ProParaleloInstructorDatosRepository datosRepository;
    public ProSemestreMateriaParaleloInstructorImpl(ProEstudianteSemestreMateriaParaleloInstructorRepository repository, ProParaleloInstructorDatosRepository datosRepository) {
        super(repository);
        this.datosRepository = datosRepository;
    }

    @Override
    public ProSemestreMateriaParaleloInstructor save(ProSemestreMateriaParaleloInstructor obj) throws DataException {
        Optional<ProSemestreMateriaParaleloInstructor> objGuardado = repository.findByCodPeriodoSemestreMateriaParaleloAndCodInstructor(obj.getCodPeriodoSemestreMateriaParalelo(), obj.getCodInstructor());
        if (objGuardado.isPresent()) {
            ProSemestreMateriaParaleloInstructor stp = objGuardado.get();
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
    public ProSemestreMateriaParaleloInstructor update(ProSemestreMateriaParaleloInstructor objActualizado) throws DataException {
        // TODO Auto-generated method stub

        Optional<ProSemestreMateriaParaleloInstructor> objGuardado = repository.findByCodPeriodoSemestreMateriaParaleloAndCodInstructor(objActualizado.getCodPeriodoSemestreMateriaParalelo(), objActualizado.getCodInstructor());
        if (objGuardado.isPresent() && !objGuardado.get().getCodPeriodoSemestreMateriaParaleloInstructor().equals(objActualizado.getCodPeriodoSemestreMateriaParaleloInstructor())) {
            throw new DataException(REGISTRO_YA_EXISTE);
        }

        return super.update(objActualizado);
    }

    @Override
    public List<ProParaleloInstructorDto> getAllByCodMateriaParalelo(Integer codigo) {
        return datosRepository.getAllByCodMateriaParalelo(codigo);
    }
}
