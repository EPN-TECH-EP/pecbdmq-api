package epntech.cbdmq.pe.servicio.impl.formacion;

import epntech.cbdmq.pe.dominio.admin.MateriaParalelo;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.AntiguedadesRepository;
import epntech.cbdmq.pe.repositorio.admin.formacion.MateriaParaleloRepository;
import epntech.cbdmq.pe.servicio.formacion.MateriaParaleloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MateriaParaleloServiceImpl implements MateriaParaleloService {
    @Autowired
    private MateriaParaleloRepository repo;
    @Override
    public List<MateriaParalelo> getMateriasParalelo() throws DataException {
        return repo.findAll();
    }
    @Override
    public MateriaParalelo saveMateriaInParalelo(MateriaParalelo obj) throws DataException {
        return repo.save(obj);
    }
}
