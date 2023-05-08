package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.admin.Menu;
import epntech.cbdmq.pe.dominio.admin.MenuPermisos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

	Menu findMenuByEtiqueta(String etiqueta)throws DataException;
	
	Optional<Menu> getById(int id);
	
	
	
	@Query(nativeQuery = true)
	List<MenuPermisos> findMenuByIdUsuario(@Param("id_usuario") String idUsuario);

}
