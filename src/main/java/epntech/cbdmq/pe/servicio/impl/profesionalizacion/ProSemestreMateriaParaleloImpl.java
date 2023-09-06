package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProSemestreMateriaParalelo;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProMateriaParaleloDto;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.repository.ProMateriaParaleloDatosRepository;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProEstudianteSemestreMateriaParaleloRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProSemestreMateriaParaleloService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

@Service
public class ProSemestreMateriaParaleloImpl extends ProfesionalizacionServiceImpl<ProSemestreMateriaParalelo, Integer, ProEstudianteSemestreMateriaParaleloRepository> implements ProSemestreMateriaParaleloService {

    private final ProMateriaParaleloDatosRepository datosRepository;

    public ProSemestreMateriaParaleloImpl(ProEstudianteSemestreMateriaParaleloRepository repository, ProMateriaParaleloDatosRepository datosRepository) {
        super(repository);
        this.datosRepository = datosRepository;
    }

    @Override
    public ProSemestreMateriaParalelo save(ProSemestreMateriaParalelo obj) throws DataException {
        Optional<ProSemestreMateriaParalelo> objGuardado;
        if (obj.getCodParalelo() == null && obj.getCodProyecto() == null) {
            throw new DataException(REGISTRO_VACIO);
        }
        if (obj.getCodParalelo() != null) {
            objGuardado = repository.findByCodSemestreMateriaAndCodParalelo(obj.getCodSemestreMateria(), obj.getCodParalelo());
        } else {
            objGuardado = repository.findByCodSemestreMateriaAndCodProyecto(obj.getCodSemestreMateria(), obj.getCodProyecto());
        }

        if (objGuardado.isPresent()) {
            ProSemestreMateriaParalelo stp = objGuardado.get();
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
    public ProSemestreMateriaParalelo update(ProSemestreMateriaParalelo objActualizado) throws DataException {
        Optional<ProSemestreMateriaParalelo> objGuardado;
        if (objActualizado.getCodParalelo() == null && objActualizado.getCodProyecto() == null) {
            throw new DataException(REGISTRO_VACIO);
        }
        if (objActualizado.getCodParalelo() != null) {
            objGuardado = repository.findByCodSemestreMateriaAndCodParalelo(objActualizado.getCodSemestreMateria(), objActualizado.getCodParalelo());
        } else {
            objGuardado = repository.findByCodSemestreMateriaAndCodProyecto(objActualizado.getCodSemestreMateria(), objActualizado.getCodProyecto());
        }

        if (objGuardado.isPresent() && !objGuardado.get().getCodSemestreMateriaParalelo().equals(objActualizado.getCodSemestreMateriaParalelo())) {
            throw new DataException(REGISTRO_YA_EXISTE);
        }

        return super.update(objActualizado);
    }

    @Override
    public List<ProMateriaParaleloDto> getAllByCodSemestreMateria(Integer codigo) {
        return datosRepository.getAllByCodSemestreMateria(codigo);
    }
}
