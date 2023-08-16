package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProEstudianteSemestre;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.profesionalizacion.ProEstudianteSemestreRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProEstudianteSemestreService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

@Service
public class ProEstudianteSemestreServiceImpl extends ProfesionalizacionServiceImpl<ProEstudianteSemestre, Integer, ProEstudianteSemestreRepository> implements ProEstudianteSemestreService {

    public ProEstudianteSemestreServiceImpl(ProEstudianteSemestreRepository repository) {
        super(repository);
    }

    @Override
    public List<ProEstudianteSemestre> getAll() {
        return repository.findAll();
    }

    public List<ProEstudianteSemestre> getBySemestre(int codigo) {
        return repository.findByCodigoSemestre(codigo);
    }

    @Override
    public Optional<ProEstudianteSemestre> getById(int id) {
        return repository.findById(id);
    }

    @Override
    public ProEstudianteSemestre save(ProEstudianteSemestre obj) throws DataException {
        Optional<ProEstudianteSemestre> proEstudianteSemestre = repository.findByCodigoEstudianteAndCodigoSemestre(obj.getCodigoSemestre(), obj.getCodigoSemestre());
        if (proEstudianteSemestre.isPresent())
            throw new DataException(REGISTRO_YA_EXISTE);
        return repository.save(obj);
    }

    @Override
    public ProEstudianteSemestre update(ProEstudianteSemestre datosGuardados) throws DataException {
        Optional<ProEstudianteSemestre> proEstudianteSemestre = repository.findById(datosGuardados.getCodigo());
        if (proEstudianteSemestre.isPresent())
            throw new DataException(REGISTRO_YA_EXISTE);
        return repository.save(datosGuardados);
    }

    @Override
    public void delete(int codigo) throws DataException {
        Optional<?> objGuardado = repository.findById(codigo);
        if (objGuardado.isEmpty()) {
            throw new DataException(REGISTRO_NO_EXISTE);
        }
        try {
            repository.deleteById(codigo);
        } catch (Exception e) {
            if (e.getMessage().contains("constraint")) {
                throw new DataException(DATOS_RELACIONADOS);
            }
        }
    }
}
