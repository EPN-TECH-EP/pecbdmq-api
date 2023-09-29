package epntech.cbdmq.pe.servicio;

import epntech.cbdmq.pe.dominio.admin.llamamiento.Funcionario;

import java.util.List;

public interface FuncionarioService {
    List<Funcionario> saveAll(List <Funcionario> funcionarios);
    Funcionario save(Funcionario funcionario);
    List<Funcionario> listAll();
}
