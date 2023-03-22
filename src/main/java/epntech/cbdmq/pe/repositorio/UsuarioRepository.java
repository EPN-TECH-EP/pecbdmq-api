package epntech.cbdmq.pe.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findUsuarioByNombreUsuario(String username);

	//@Override
	@EntityGraph(attributePaths = "codDatosPersonales")
	List<Usuario> findAll();

    //Usuario findUsuarioByEmail(String email);
    
    
}
