package epntech.cbdmq.pe.servicio.impl;

import epntech.cbdmq.pe.dominio.admin.Cargo;
import epntech.cbdmq.pe.repositorio.admin.CargoRepository;
import epntech.cbdmq.pe.repositorio.admin.GradoRepository;
import epntech.cbdmq.pe.servicio.CargoService;
import epntech.cbdmq.pe.servicio.GradoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoServiceImpl implements CargoService {

    @Autowired
    CargoRepository repo;
    @Override
    public List<Cargo> getAll() {
        return repo.findAll();
    }

    @Override
    public Cargo findByNombre(String nombre) {
        return repo.findByNombreIgnoreCase(nombre).orElse(null);
    }
}
