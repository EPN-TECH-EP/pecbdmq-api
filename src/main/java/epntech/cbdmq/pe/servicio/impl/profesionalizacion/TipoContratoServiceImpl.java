package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.dominio.admin.profesionalizacion.TipoContrato;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.profesionalizacion.TipoContratoRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.TipoContratoService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

@Service
public class TipoContratoServiceImpl extends ProfesionalizacionServiceImpl<TipoContrato, Integer, TipoContratoRepository> implements TipoContratoService {
    public TipoContratoServiceImpl(TipoContratoRepository repository){
        super(repository);
    }
    @Override
    public Optional<TipoContrato> findByNombreTipo(String nombreTipo) {
        return repository.findByNombre(nombreTipo);

    }
   @Override
    public TipoContrato save(TipoContrato obj) throws DataException {
        Optional<TipoContrato> tipoContrato=repository.findByNombre(obj.getNombre());
        if (tipoContrato.isPresent())
            throw new DataException(REGISTRO_YA_EXISTE);
        return super.save(obj);
    }

    @Override
    public TipoContrato update(TipoContrato datosGuardados) throws DataException {
        Optional<TipoContrato> tipoContrato=repository.findByNombre(datosGuardados.getNombre());
        if (tipoContrato.isPresent())
            throw new DataException(REGISTRO_YA_EXISTE);
        return super.update(datosGuardados);
    }
}
