package epntech.cbdmq.pe.servicio.impl;

import epntech.cbdmq.pe.dominio.admin.Grado;
import epntech.cbdmq.pe.dominio.admin.Rango;
import epntech.cbdmq.pe.repositorio.admin.GradoRepository;
import epntech.cbdmq.pe.repositorio.admin.RangoRepository;
import epntech.cbdmq.pe.servicio.GradoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradoServiceImpl implements GradoService {
    @Autowired
    GradoRepository repo;
    @Autowired
    RangoRepository repoRa;
    @Override
    public List<Grado> getAll() {
        return repo.findAll();
    }

    @Override
    public List<Rango> getRangoByGrado(Integer codGrado) {
        return repoRa.findByGrado(codGrado);
    }

    @Override
    public Grado findByNombre(String nombre) {
        return repo.findByNombreIgnoreCase(nombre).orElse(null);
    }
}
