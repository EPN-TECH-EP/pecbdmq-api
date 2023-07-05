package epntech.cbdmq.pe.servicio.impl;

import epntech.cbdmq.pe.dominio.admin.EjeMateria;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.EjeMateriaRepository;
import epntech.cbdmq.pe.servicio.EjeMateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.MensajesConst.*;

@Service
public class EjeMateriaServiceImpl implements EjeMateriaService {
    @Autowired
    private EjeMateriaRepository repo;
    @Override
    public EjeMateria saveEje(EjeMateria obj) throws DataException {
        if(obj.getNombreEjeMateria().trim().isEmpty()) {
            throw new DataException(REGISTRO_VACIO);
        }
        return repo.save(obj);
    }

    @Override
    public List<EjeMateria> getAllEje()  {
        return repo.findAll();
    }

    @Override
    public Optional<EjeMateria> getByIdEje(Long id)  {
        return repo.findById(id);
    }

    @Override
    public EjeMateria updateEje(EjeMateria objActualizado) throws DataException {
        if(objActualizado.getNombreEjeMateria()!=null) {
            Optional<EjeMateria> objGuardado = repo.findByNombreEjeMateriaIgnoreCase(objActualizado.getNombreEjeMateria());
            if (objGuardado.isPresent()&& !objGuardado.get().getCoddEjeMateria().equals(objActualizado.getCoddEjeMateria())) {
                throw new DataException(REGISTRO_YA_EXISTE);
            }
        }
        return repo.save(objActualizado);
    }

    @Override
    public void deleteEje(Long id) throws DataException {
        Optional<?> objGuardado = repo.findById(id);
        if (objGuardado.isEmpty()) {
            throw new DataException(REGISTRO_NO_EXISTE);
        }
        try {
            repo.deleteById(id);
        } catch (Exception e) {
            if (e.getMessage().contains("constraint")) {
                throw new DataException(DATOS_RELACIONADOS);
            }
        }

    }
}
