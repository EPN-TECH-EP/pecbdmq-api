package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import epntech.cbdmq.pe.dominio.admin.MenuRol;
import epntech.cbdmq.pe.dominio.admin.MenuRolId;

public interface MenuRolRepository extends JpaRepository<MenuRol, MenuRolId>{
	
    @Query("select mr FROM MenuRol mr WHERE mr.id.codRol = :codRol")    
	List<MenuRol> findByCodRol(@Param("codRol") Integer codRol);
	
	//Optional<MenuRol> findByCodRolAndCodMenu(Integer codMenu,	Integer codRol);

}
