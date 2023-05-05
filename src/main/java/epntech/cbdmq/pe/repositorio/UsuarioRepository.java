package epntech.cbdmq.pe.repositorio;

import java.util.List;

import epntech.cbdmq.pe.dominio.util.UsuarioDtoRead;
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
	@Query(value = "select u from Usuario u where lower(u.codDatosPersonales.apellido) like lower(concat('%', :apellido, '%')) or lower(u.codDatosPersonales.nombre) like lower(concat('%', :nombre, '%'))")
	public List<Usuario> findUsuariosByNombreApellido(@Param("nombre") String nombre, @Param("apellido") String apellido) ;
	@Query(value = "SELECT u FROM Usuario u WHERE u.codDatosPersonales.correo_personal like %:correo%")
	public List<Usuario> findUsuariosByCorreo(@Param("correo") String correo) ;

	@Query(value = "SELECT u FROM Usuario u")
	List<Usuario> findAllPageable(Pageable pageable);
	@Query(nativeQuery = true)
	List<UsuarioDtoRead> buscarUsuarioPersonalizado();

    
    
}
