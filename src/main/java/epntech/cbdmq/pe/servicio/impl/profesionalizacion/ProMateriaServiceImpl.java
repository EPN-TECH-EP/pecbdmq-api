package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.admin.EjeMateria;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProMateria;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProMateriaSemestreDto;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.repository.ProMateriaSemestreDatosRepository;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.EjeMateriaRepository;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProMateriaRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProMateriaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

@Service
public class ProMateriaServiceImpl  extends ProfesionalizacionServiceImpl<ProMateria, Integer, ProMateriaRepository> implements ProMateriaService {
    private final ProMateriaSemestreDatosRepository datosRepository;
    private final EjeMateriaRepository ejeMateriaRepository;
    public  ProMateriaServiceImpl(ProMateriaRepository repository, ProMateriaSemestreDatosRepository datosRepository, EjeMateriaRepository ejeMateriaRepository){
        super(repository);
        this.datosRepository = datosRepository;
        this.ejeMateriaRepository = ejeMateriaRepository;
    }
    @Override
    public ProMateria save(ProMateria obj) throws DataException {
        if(obj.getNombre().trim().isEmpty())
            throw new DataException(REGISTRO_VACIO);
        Optional<ProMateria> objGuardado = repository.findByNombreIgnoreCase(obj.getNombre());
        if (objGuardado.isPresent()) {
            ProMateria stp = objGuardado.get();
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
    public ProMateria update(ProMateria objActualizado) throws DataException {
        // TODO Auto-generated method stub
        if(objActualizado.getNombre().trim().isEmpty())
            throw new DataException(REGISTRO_VACIO);
        Optional<ProMateria> objGuardado = repository.findByNombreIgnoreCase(objActualizado.getNombre());
        if (objGuardado.isPresent()&& !objGuardado.get().getCodMateria().equals(objActualizado.getCodMateria())) {
            throw new DataException(REGISTRO_YA_EXISTE);
        }

        return super.update(objActualizado);
    }

    public List<EjeMateria> getEjeMateria(){
        return ejeMateriaRepository.findAll();
    }

    public List<ProMateriaSemestreDto> getByAll(){
        return datosRepository.getByAll();
    }

    public List<ProMateriaSemestreDto> getByAll(Integer codSemestre, Integer codPeriodo){
        return datosRepository.getAllByCodSemestreAndCodPeriodo(codSemestre, codPeriodo);
    }

    public List<ProMateriaSemestreDto> getByAll(Integer codSemestre){
        return datosRepository.getAllByCodSemestre(codSemestre);
    }

    public List<ProMateriaSemestreDto> getByAllPeriodo(Integer codSemestre){
        return datosRepository.getAllByCodPeriodo(codSemestre);
    }
}
