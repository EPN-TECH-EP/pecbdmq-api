package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProInscripcionesDelegados;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProInscripcionesDelegadosDto;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.repository.ProInscripcionesDelegadosDatosRepository;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProInscripcionesDelegadosRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProInscripcionesDelegadosService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

@Service
public class ProInscripcionesDelegadosServiceImpl extends ProfesionalizacionServiceImpl<ProInscripcionesDelegados, Integer, ProInscripcionesDelegadosRepository> implements ProInscripcionesDelegadosService {

    private final ProInscripcionesDelegadosDatosRepository datosRepository;

    public ProInscripcionesDelegadosServiceImpl(ProInscripcionesDelegadosRepository repository, ProInscripcionesDelegadosDatosRepository datosRepository) {
        super(repository);
        this.datosRepository = datosRepository;
    }

    @Override
    public ProInscripcionesDelegados save(ProInscripcionesDelegados obj) throws DataException {


        Optional<ProInscripcionesDelegados> objGuardado = repository.findByCodInscripcionesAndCodDelegados(obj.getCodInscripciones(), obj.getCodDelegados());
        if (objGuardado.isPresent()) {

            ProInscripcionesDelegados stp = objGuardado.get();
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
    public ProInscripcionesDelegados update(ProInscripcionesDelegados objActualizado) throws DataException {
        // TODO Auto-generated method stub

        Optional<ProInscripcionesDelegados> objGuardado = repository.findByCodInscripcionesAndCodDelegados(objActualizado.getCodInscripciones(), objActualizado.getCodDelegados());
        if (objGuardado.isPresent() && !objGuardado.get().getCodInscripcionesDelegados().equals(objActualizado.getCodInscripcionesDelegados())) {
            throw new DataException(REGISTRO_YA_EXISTE);
        }

        return super.update(objActualizado);
    }

    public List<ProInscripcionesDelegadosDto> findByCodConvocatoria(Integer cod_convocatoria) {
        return datosRepository.findByCodConvocatoria(cod_convocatoria);
    }

    public List<ProInscripcionesDelegadosDto> findByCodConvocatoriaAnAndCodDelegados(Integer cod_convocatoria, Integer cod_delegado) {
        return datosRepository.findByCodConvocatoriaAnAndCodDelegados(cod_convocatoria, cod_delegado);
    }
}
