package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProNotaProfesionalizacion;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProNotasProfesionalizacionDto;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProParaleloEstudianteDto;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.repository.ProNotasProfesionalizacionDatosRepository;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.repository.ProParaleloEstudianteDatosRepository;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProNotaProfesionalizacionRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProNotaProfesionalizacionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

@Service
public class ProNotaProfesionalizacionServiceImpl extends ProfesionalizacionServiceImpl<ProNotaProfesionalizacion, Integer, ProNotaProfesionalizacionRepository> implements ProNotaProfesionalizacionService {

    private final ProNotasProfesionalizacionDatosRepository datosRepository;
    private final ProParaleloEstudianteDatosRepository estudianteDatosRepository;

    public ProNotaProfesionalizacionServiceImpl(ProNotaProfesionalizacionRepository repository, ProNotasProfesionalizacionDatosRepository datosRepository, ProParaleloEstudianteDatosRepository estudianteDatosRepository) {
        super(repository);
        this.datosRepository = datosRepository;
        this.estudianteDatosRepository = estudianteDatosRepository;
    }

    @Override
    public ProNotaProfesionalizacion save(ProNotaProfesionalizacion obj) throws DataException {
        Optional<ProNotaProfesionalizacion> objGuardado = repository.findByCodEstudianteSemestreMateriaParaleloAndCodInstructorAndCodMateriaAndCodEstudianteAndCodSemestre(obj.getCodEstudianteSemestreMateriaParalelo(),
                obj.getCodInstructor(),
                obj.getCodMateria(),
                obj.getCodEstudiante(),
                obj.getCodSemestre());
        if (objGuardado.isPresent()) {
            ProNotaProfesionalizacion stp = objGuardado.get();
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
    public ProNotaProfesionalizacion update(ProNotaProfesionalizacion objActualizado) throws DataException {
        Optional<ProNotaProfesionalizacion> objGuardado = repository.findByCodEstudianteSemestreMateriaParaleloAndCodInstructorAndCodMateriaAndCodEstudianteAndCodSemestre(
                objActualizado.getCodEstudianteSemestreMateriaParalelo(),
                objActualizado.getCodInstructor(),
                objActualizado.getCodMateria(),
                objActualizado.getCodEstudiante(),
                objActualizado.getCodSemestre());
        if (objGuardado.isPresent() && !objGuardado.get().getCodNotaProfesionalizacion().equals(objActualizado.getCodNotaProfesionalizacion())) {
            throw new DataException(REGISTRO_YA_EXISTE);
        }
        return super.update(objActualizado);
    }

    public List<ProNotasProfesionalizacionDto> findByMateriaParalelo(Integer codMateriaParalelo) {
        List<ProParaleloEstudianteDto> estudianteDtos = estudianteDatosRepository.findByMateriaParalelo(codMateriaParalelo);
        List<ProNotasProfesionalizacionDto> notasProfesionalizacionDtos = datosRepository.findByMateriaParalelo(codMateriaParalelo);
        estudianteDtos.forEach(estudianteDto -> {
            ProNotasProfesionalizacionDto first = notasProfesionalizacionDtos.stream().filter(proNotasProfesionalizacionDto -> Objects.equals(estudianteDto.getCodEstudiante(), proNotasProfesionalizacionDto.getCodEstudiante())).findFirst().orElse(null);
            if (first == null) {
                first = new ProNotasProfesionalizacionDto();
                first.setApellido(estudianteDto.getApellido());
                first.setNombre(estudianteDto.getNombre());
                first.setCodEstudiante(estudianteDto.getCodEstudiante());
                first.setCodEstudianteSemestreMateriaParalelo(estudianteDto.getCodMateriaParalelo());
                first.setNombreParalelo(estudianteDto.getNombreParalelo());
                notasProfesionalizacionDtos.add(first);
            }
        });
        return notasProfesionalizacionDtos;
    }

    public List<ProNotasProfesionalizacionDto> getByAll(Integer codSemestre, Integer codPeriodo){
        return datosRepository.getAllByCodSemestreAndCodPeriodo(codSemestre, codPeriodo);
    }

    public List<ProNotasProfesionalizacionDto> getByAll(Integer codSemestre){
        return datosRepository.getAllByCodSemestre(codSemestre);
    }

    public List<ProNotasProfesionalizacionDto> getByAll(){
        return datosRepository.getByAll();
    }

    public List<ProNotasProfesionalizacionDto> getByAllPeriodo(Integer codSemestre){
        return datosRepository.getAllByCodPeriodo(codSemestre);
    }

}
