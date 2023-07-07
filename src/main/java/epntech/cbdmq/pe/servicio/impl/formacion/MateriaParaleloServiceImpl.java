package epntech.cbdmq.pe.servicio.impl.formacion;

import epntech.cbdmq.pe.dominio.admin.MateriaParalelo;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.AntiguedadesRepository;
import epntech.cbdmq.pe.repositorio.admin.formacion.MateriaParaleloRepository;
import epntech.cbdmq.pe.servicio.formacion.MateriaParaleloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MateriaParaleloServiceImpl implements MateriaParaleloService {
    @Autowired
    private MateriaParaleloRepository repo;
    @Override
    public List<MateriaParalelo> getMateriasParalelo() throws DataException {
        return repo.findAll();
    }

    @Override
    public Optional<MateriaParalelo> getById(Integer codigo) {
        return repo.findById(codigo);
    }

    @Override
    public Optional<MateriaParalelo> findByEstudianteMateriaParalelo(Integer codigoEstudianteMateria) {
        return repo.findByEstudianteMateriaParalelo(codigoEstudianteMateria);
    }

    @Override
    public MateriaParalelo saveMateriaParalelo(MateriaParalelo obj) throws DataException {
        return repo.save(obj);
    }
}
