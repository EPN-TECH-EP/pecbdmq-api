package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProInstructor;
import epntech.cbdmq.pe.dominio.util.InstructorDatos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.InstructorDatosRepository;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProInstructorRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProInstructorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

@Service
public class ProInstructorServiceImpl implements ProInstructorService {


    private final ProInstructorRepository repo;
    private final InstructorDatosRepository instructorDatosRepository;

    public ProInstructorServiceImpl(ProInstructorRepository repo, InstructorDatosRepository instructorDatosRepository) {
        this.repo = repo;
        this.instructorDatosRepository = instructorDatosRepository;
    }

    @Override
    public List<InstructorDatos> getAll() {
        // TODO Auto-generated method stub
        return instructorDatosRepository.getAllInstructorDatosProfesionalizacion();
    }

    @Override
    public Optional<ProInstructor> getById(int id) {
        // TODO Auto-generated method stub
        return repo.findById(id);
    }


    @Override
    public ProInstructor save(ProInstructor obj) throws DataException {

        Optional<ProInstructor> objGuardado = repo.findByCodDatosPersonales(obj.getCodDatosPersonales());
        if (objGuardado.isPresent()) {

            ProInstructor stp = objGuardado.get();
            if (stp.getEstado().compareToIgnoreCase(EstadosConst.ELIMINADO) == 0) {
                stp.setEstado(EstadosConst.ACTIVO);
                return repo.save(stp);
            } else {
                throw new DataException(REGISTRO_YA_EXISTE);
            }
        }
        return repo.save(obj);
    }

    @Override
    public ProInstructor update(ProInstructor objActualizado) throws DataException {
        // TODO Auto-generated method stub

        Optional<ProInstructor> objGuardado = repo.findByCodDatosPersonales(objActualizado.getCodDatosPersonales());
        if (objGuardado.isPresent() && !objGuardado.get().getCodInstructor().equals(objActualizado.getCodInstructor())) {
            throw new DataException(REGISTRO_YA_EXISTE);
        }
        objActualizado.setEstado(EstadosConst.ACTIVO);

        return repo.save(objActualizado);
    }

    @Override
    public void delete(Integer codInstructor) {
        // TODO Auto-generated method stub
        repo.deleteById(codInstructor);
    }
}
