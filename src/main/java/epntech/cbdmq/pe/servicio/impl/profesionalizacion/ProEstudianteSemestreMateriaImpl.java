package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProEstudianteSemestreMateria;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProEstudianteSemestreMateriaRepository;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProEstudianteSemestreMateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

@Service
public class ProEstudianteSemestreMateriaImpl implements ProEstudianteSemestreMateriaService {

    @Autowired
    private ProEstudianteSemestreMateriaRepository repo;

    @Override
    public List<ProEstudianteSemestreMateria> getAll() {
        // TODO Auto-generated method stub
        return repo.findAll();
    }

    @Override
    public Optional<ProEstudianteSemestreMateria> getById(int id) {
        // TODO Auto-generated method stub
        return repo.findById(id);
    }


    @Override
    public ProEstudianteSemestreMateria save(ProEstudianteSemestreMateria obj) throws DataException {


        Optional<ProEstudianteSemestreMateria> objGuardado = repo.findByCodPeriodoAndCodPeriodoEstudianteSemestreAndCodMateria(obj.getCodPeriodo(), obj.getCodPeriodoEstudianteSemestre(), obj.getCodMateria());
        if (objGuardado.isPresent()) {

            ProEstudianteSemestreMateria stp = objGuardado.get();
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
    public ProEstudianteSemestreMateria update(ProEstudianteSemestreMateria objActualizado) throws DataException {
        // TODO Auto-generated method stub

        Optional<ProEstudianteSemestreMateria> objGuardado = repo.findByCodPeriodoAndCodPeriodoEstudianteSemestreAndCodMateria(objActualizado.getCodPeriodo(), objActualizado.getCodPeriodoEstudianteSemestre(), objActualizado.getCodMateria());
        if (objGuardado.isPresent()&& !objGuardado.get().getCodPeriodoEstudianteSemestreMateria().equals(objActualizado.getCodPeriodoEstudianteSemestreMateria())) {
            throw new DataException(REGISTRO_YA_EXISTE);
        }

        return repo.save(objActualizado);
    }

    @Override
    public void delete(Integer codPeriodoEstudianteSemestreMateria) {
        // TODO Auto-generated method stub
        repo.deleteById(codPeriodoEstudianteSemestreMateria);
    }
}
