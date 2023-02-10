package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import epntech.cbdmq.pe.dominio.admin.Menu;
import epntech.cbdmq.pe.dominio.admin.MenuPermisos;

public interface MenuRepository extends JpaRepository<Menu, Long> {

	Menu findMenuByEtiqueta(String etiqueta);
	
	@Query(nativeQuery = true)
	List<MenuPermisos> findMenuByIdUsuario(@Param("id_usuario") String idUsuario);

}
