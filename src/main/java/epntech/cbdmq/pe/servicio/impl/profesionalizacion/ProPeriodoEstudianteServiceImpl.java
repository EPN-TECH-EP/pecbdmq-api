package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.admin.UsuarioDatoPersonal;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProPeriodoEstudiante;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProPeriodoEstudianteDto;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.repository.ProPeriodoEstudianteDatosRepository;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.UsuarioDatoPersonalRepository;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProPeriodoEstudianteRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProPeriodoEstudianteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

@Service
public class ProPeriodoEstudianteServiceImpl extends ProfesionalizacionServiceImpl<ProPeriodoEstudiante, Integer, ProPeriodoEstudianteRepository> implements ProPeriodoEstudianteService {
    private final ProPeriodoEstudianteDatosRepository datosRepository;
    private final UsuarioDatoPersonalRepository usuarioDatoPersonalRepository;
    public ProPeriodoEstudianteServiceImpl(ProPeriodoEstudianteRepository repository, ProPeriodoEstudianteDatosRepository datosRepository, UsuarioDatoPersonalRepository usuarioDatoPersonalRepository) {
        super(repository);
        this.datosRepository = datosRepository;
        this.usuarioDatoPersonalRepository = usuarioDatoPersonalRepository;
    }

    @Override
    public ProPeriodoEstudiante save(ProPeriodoEstudiante obj) throws DataException {
        Optional<ProPeriodoEstudiante> objGuardado = repository.findByCodPeriodoAndCodDatosPersonales(obj.getCodPeriodo(), obj.getCodDatosPersonales());
        if (objGuardado.isPresent()) {
            ProPeriodoEstudiante stp = objGuardado.get();
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
    public ProPeriodoEstudiante update(ProPeriodoEstudiante objActualizado) throws DataException {
        // TODO Auto-generated method stub

        Optional<ProPeriodoEstudiante> objGuardado = repository.findByCodPeriodoAndCodDatosPersonales(objActualizado.getCodPeriodo(), objActualizado.getCodDatosPersonales());
        if (objGuardado.isPresent()&& !objGuardado.get().getCodPeriodoEstudiante().equals(objActualizado.getCodPeriodoEstudiante())) {
            throw new DataException(REGISTRO_YA_EXISTE);
        }

        return super.update(objActualizado);
    }

    public List<ProPeriodoEstudianteDto> getAllByPeriodo(Integer codigo) {
        return datosRepository.getAllByCodPeriodo(codigo);
    }

    public UsuarioDatoPersonal findByCedula(String cedula){
        return usuarioDatoPersonalRepository.getByCedulaProfesionalizacion(cedula);
    }

    public List<UsuarioDatoPersonal> findByNombreApellido(String nombre, String apellido){
        return usuarioDatoPersonalRepository
                .getByCedulaProfesionalizacionNombreApellido(
                        nombre.trim().isEmpty()? null : nombre.toLowerCase(),
                        apellido.trim().isEmpty()? null : apellido.toLowerCase()
                );
    }

    public List<UsuarioDatoPersonal> findByCorreo(String email){
        return usuarioDatoPersonalRepository.getByCedulaProfesionalizacionEmail(email);
    }

}
