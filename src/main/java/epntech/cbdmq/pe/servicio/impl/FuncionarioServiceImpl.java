package epntech.cbdmq.pe.servicio.impl;

import epntech.cbdmq.pe.dominio.admin.llamamiento.Funcionario;
import epntech.cbdmq.pe.repositorio.admin.FuncionarioRepository;
import epntech.cbdmq.pe.servicio.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FuncionarioServiceImpl implements FuncionarioService {
    @Autowired
    private FuncionarioRepository repo;
    @Override
    public List<Funcionario> saveAll(List<Funcionario> funcionarios) {
        return repo.saveAll(funcionarios);
    }

    @Override
    public Funcionario save(Funcionario funcionario) {
        return repo.save(funcionario);
    }

    @Override
    public List<Funcionario> listAll() {
        return repo.findAll();
    }
}
