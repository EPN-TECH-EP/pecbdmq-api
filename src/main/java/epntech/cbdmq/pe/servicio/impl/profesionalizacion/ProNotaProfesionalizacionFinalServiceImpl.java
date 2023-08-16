package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProNotaProfesionalizacionFinal;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProNotasProfesionalizacionFinalDto;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProParaleloEstudianteDto;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.repository.ProNotasProfesionalizacionFinalDatosRepository;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.repository.ProParaleloEstudianteDatosRepository;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProNotaProfesionalizacionFinalRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProNotaProfesionalizacionFinalService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

@Service
public class ProNotaProfesionalizacionFinalServiceImpl extends ProfesionalizacionServiceImpl<ProNotaProfesionalizacionFinal, Integer, ProNotaProfesionalizacionFinalRepository> implements ProNotaProfesionalizacionFinalService {

    public final ProNotasProfesionalizacionFinalDatosRepository datosRepository;
    private final ProParaleloEstudianteDatosRepository estudianteDatosRepository;
    public ProNotaProfesionalizacionFinalServiceImpl(ProNotaProfesionalizacionFinalRepository repository, ProNotasProfesionalizacionFinalDatosRepository datosRepository, ProParaleloEstudianteDatosRepository estudianteDatosRepository){
        super(repository);
        this.datosRepository = datosRepository;
        this.estudianteDatosRepository = estudianteDatosRepository;
    }
    @Override
    public Optional<ProNotaProfesionalizacionFinal> findByCodEstudianteSemestreAndCodEstudianteAndCodSemestre(Integer codEstudianteSemestre, Integer codEstudiante, Integer codSemestre){
        return repository.findByCodEstudianteSemestreAndCodEstudianteAndCodSemestre( codEstudianteSemestre, codEstudiante, codSemestre);
    }
    @Override
    public ProNotaProfesionalizacionFinal save(ProNotaProfesionalizacionFinal obj) throws DataException {
        Optional<ProNotaProfesionalizacionFinal> objGuardado = repository.findByCodEstudianteSemestreAndCodEstudianteAndCodSemestre(obj.getCodEstudianteSemestre(),
                obj.getCodEstudiante(),
                obj.getCodSemestre());
        if (objGuardado.isPresent()) {
            ProNotaProfesionalizacionFinal stp = objGuardado.get();
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
    public ProNotaProfesionalizacionFinal update(ProNotaProfesionalizacionFinal objActualizado) throws DataException {
        // TODO Auto-generated method stub

        Optional<ProNotaProfesionalizacionFinal> objGuardado = repository.findByCodEstudianteSemestreAndCodEstudianteAndCodSemestre(
                objActualizado.getCodEstudianteSemestre(),
                objActualizado.getCodEstudiante(),
                objActualizado.getCodSemestre());

        if (objGuardado.isPresent()&& !objGuardado.get().getCodNotaProfesionalizacionFinal().equals(objActualizado.getCodNotaProfesionalizacionFinal())) {
            throw new DataException(REGISTRO_YA_EXISTE);
        }
        return super.update(objActualizado);
    }

    public List<ProNotasProfesionalizacionFinalDto> findByMateriaParalelo(Integer codPeriodo, Integer codSemestre) {
        List<ProParaleloEstudianteDto> estudianteDtos = estudianteDatosRepository.findByCodPeriodoAndCodSemestre(codPeriodo, codSemestre);
        List<ProNotasProfesionalizacionFinalDto> notasProfesionalizacionDtos = datosRepository.findByCodPeriodoAndCodSemestre(codPeriodo, codSemestre);
        estudianteDtos.forEach(estudianteDto -> {
            ProNotasProfesionalizacionFinalDto first = notasProfesionalizacionDtos.stream().filter(proNotasProfesionalizacionDto -> Objects.equals(estudianteDto.getCodEstudiante(), proNotasProfesionalizacionDto.getCodEstudiante())).findFirst().orElse(null);
            if (first == null) {
                first = new ProNotasProfesionalizacionFinalDto();
                first.setApellido(estudianteDto.getApellido());
                first.setNombre(estudianteDto.getNombre());
                first.setCodEstudiante(estudianteDto.getCodEstudiante());
                notasProfesionalizacionDtos.add(first);
            }
        });
        return notasProfesionalizacionDtos;
    }
}
