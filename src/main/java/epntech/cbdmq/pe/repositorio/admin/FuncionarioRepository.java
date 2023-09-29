package epntech.cbdmq.pe.repositorio.admin;

import epntech.cbdmq.pe.dominio.admin.llamamiento.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario,Integer> {
}
