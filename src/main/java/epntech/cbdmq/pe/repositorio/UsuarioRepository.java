package epntech.cbdmq.pe.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findUsuarioByNombreUsuario(String username);

    //Usuario findUsuarioByEmail(String email);
}
