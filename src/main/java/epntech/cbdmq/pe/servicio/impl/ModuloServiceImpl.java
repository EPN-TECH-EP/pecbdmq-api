package epntech.cbdmq.pe.servicio.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.Modulo;
import epntech.cbdmq.pe.repositorio.admin.ModuloRepository;
import epntech.cbdmq.pe.servicio.ModuloService;

@Service
public class ModuloServiceImpl implements ModuloService {
    @Autowired
    private ModuloRepository repo;

    @Override
    public Modulo save(Modulo obj) {
        // TODO Auto-generated method stub
        return repo.save(obj);
    }

    @Override
    public List<Modulo> getAll() {
        // TODO Auto-generated method stub
        return repo.findAll();
    }

    @Override
    public Modulo update(Modulo objActualizado) {
        // TODO Auto-generated method stub
        return repo.save(objActualizado);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Modulo> getById(Integer codigo) {
        // TODO Auto-generated method stub
        return repo.findById(codigo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer codigo) {
        // TODO Auto-generated method stub
        repo.deleteById(codigo);
    }
}
