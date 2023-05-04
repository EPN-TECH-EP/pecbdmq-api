package epntech.cbdmq.pe.repositorio;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import epntech.cbdmq.pe.dominio.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findUsuarioByNombreUsuario(String username);

	//@Override
	@EntityGraph(attributePaths = "codDatosPersonales")
	List<Usuario> findAll();

    //Usuario findUsuarioByEmail(String email);
	
	@Query(value = "select u from Usuario u where u.codDatosPersonales.apellido like %:apellido% or u.codDatosPersonales.nombre like %:nombre%")
	public List<Usuario> findUsuariosByNombreApellido(@Param("nombre") String nombre, @Param("apellido") String apellido) ;

	@Query(value = "SELECT u FROM Usuario u")
	List<Usuario> findAllPageable(Pageable pageable);

    
    
}
