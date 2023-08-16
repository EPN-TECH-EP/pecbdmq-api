package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.dominio.profesionalizacion.ProSemestreMateriaParalelo;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProSemestreMateriaParaleloEstudiante;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProParaleloEstudianteDto;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.repository.ProParaleloEstudianteDatosRepository;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProEstudianteSemestreMateriaParaleloRepository;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProParaleloEstudianteRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProParaleloEstudianteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE_PARALELO;

@Service
public class ProParaleloEstudianteServiceImpl extends ProfesionalizacionServiceImpl<ProSemestreMateriaParaleloEstudiante, Integer, ProParaleloEstudianteRepository> implements ProParaleloEstudianteService {
    private final ProParaleloEstudianteDatosRepository datosRepository;
    private final ProEstudianteSemestreMateriaParaleloRepository materiaParaleloRepository;

    public ProParaleloEstudianteServiceImpl(ProParaleloEstudianteRepository repository, ProParaleloEstudianteDatosRepository datosRepository, ProEstudianteSemestreMateriaParaleloRepository materiaParaleloRepository) {
        super(repository);
        this.datosRepository = datosRepository;
        this.materiaParaleloRepository = materiaParaleloRepository;
    }

    public List<ProParaleloEstudianteDto> findByMateriaParalelo(Integer codMateriaParalelo) {
        return datosRepository.findByMateriaParalelo(codMateriaParalelo);
    }

    @Override
    public ProSemestreMateriaParaleloEstudiante save(ProSemestreMateriaParaleloEstudiante obj) throws DataException {
        Optional<ProSemestreMateriaParaleloEstudiante> proMateriaSemestre = repository.findByCodSemestreMateriaParaleloAndCodEstudiante(obj.getCodSemestreMateriaParalelo(), obj.getCodEstudiante());
        if (proMateriaSemestre.isPresent())
            throw new DataException(REGISTRO_YA_EXISTE);
        Optional<ProSemestreMateriaParalelo> materiaParalelo = materiaParaleloRepository.findById(obj.getCodSemestreMateriaParalelo());
        if (materiaParalelo.isPresent()){
            Integer codSemestreMateria = materiaParalelo.get().getCodSemestreMateria();
            for (ProSemestreMateriaParalelo proSemestreMateriaParalelo : materiaParaleloRepository.findByCodSemestreMateria(codSemestreMateria)) {
                if (!proSemestreMateriaParalelo.getCodSemestreMateriaParalelo().equals(obj.getCodSemestreMateriaParalelo())){
                    Optional<ProSemestreMateriaParaleloEstudiante> andCodEstudiante = repository.findByCodSemestreMateriaParaleloAndCodEstudiante(proSemestreMateriaParalelo.getCodSemestreMateriaParalelo(), obj.getCodEstudiante());
                    if (andCodEstudiante.isPresent())
                        throw new DataException(REGISTRO_YA_EXISTE_PARALELO);
                }
            }
        }
        return super.save(obj);
    }

    @Override
    public ProSemestreMateriaParaleloEstudiante update(ProSemestreMateriaParaleloEstudiante datosGuardados) throws DataException {
        Optional<ProSemestreMateriaParaleloEstudiante> proMateriaSemestre = repository.findByCodSemestreMateriaParaleloAndCodEstudiante(datosGuardados.getCodSemestreMateriaParalelo(), datosGuardados.getCodEstudiante());
        if (proMateriaSemestre.isPresent() && !Objects.equals(datosGuardados.getCodParaleloEstudiante(), proMateriaSemestre.get().getCodParaleloEstudiante()))
            throw new DataException(REGISTRO_YA_EXISTE);
        return super.update(datosGuardados);

    }
}
