package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProMateriaSemestre;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProMateriaSemestreDto;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.repository.ProMateriaSemestreDatosRepository;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.profesionalizacion.ProMateriaSemestreRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProMateriaSemestreService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

@Service
public class ProMateriaSemestreServiceImpl extends ProfesionalizacionServiceImpl<ProMateriaSemestre, Integer, ProMateriaSemestreRepository> implements ProMateriaSemestreService {
    private final ProMateriaSemestreDatosRepository datosRepository;
    public ProMateriaSemestreServiceImpl(ProMateriaSemestreRepository repository, ProMateriaSemestreDatosRepository datosRepository){

        super(repository);
        this.datosRepository = datosRepository;
    }

    @Override
    public Optional<ProMateriaSemestre> findByCodMateriaAndCodPeriodoSemestre(Integer codMateria, Integer codPeriodoSemestre) {
        return repository.findByCodMateriaAndCodPeriodoSemestre(codMateria, codPeriodoSemestre );

    }

    @Override
    public List<ProMateriaSemestreDto> findByCodigo(Integer codigo) {
        return datosRepository.getAllByCodMateriaSemestre(codigo);
    }

    @Override
    public ProMateriaSemestre save(ProMateriaSemestre obj) throws DataException{
        Optional<ProMateriaSemestre> proMateriaSemestre=repository.findByCodMateriaAndCodPeriodoSemestre(obj.getCodMateria(), obj.getCodPeriodoSemestre());
        if (proMateriaSemestre.isPresent())
            throw new DataException(REGISTRO_YA_EXISTE);
        return super.save(obj);
    }
    @Override
    public ProMateriaSemestre update (ProMateriaSemestre datosGuardados) throws  DataException{
        Optional<ProMateriaSemestre> proMateriaSemestre=repository.findById(datosGuardados.getCodigo());
        if (proMateriaSemestre.isPresent() && !Objects.equals(datosGuardados.getCodigo(), proMateriaSemestre.get().getCodigo()))
            throw new DataException(REGISTRO_YA_EXISTE);
        return super.update(datosGuardados);

    }

}
