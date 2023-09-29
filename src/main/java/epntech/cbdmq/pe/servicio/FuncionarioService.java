package epntech.cbdmq.pe.servicio;

import epntech.cbdmq.pe.dominio.admin.llamamiento.Funcionario;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

import java.util.List;

public interface FuncionarioService {
    List<Funcionario> saveAll(List <Funcionario> funcionarios);
    Funcionario save(Funcionario funcionario) throws DataException;
    List<Funcionario> listAll();
}
