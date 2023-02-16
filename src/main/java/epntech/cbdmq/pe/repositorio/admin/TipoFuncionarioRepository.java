package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.TipoFuncionario;


public interface TipoFuncionarioRepository extends JpaRepository<TipoFuncionario, Integer> {

	Optional<TipoFuncionario> findByNombre(String nombre);
}
