package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import epntech.cbdmq.pe.dominio.admin.RolUsuario;
import epntech.cbdmq.pe.dominio.admin.RolUsuario;
import epntech.cbdmq.pe.dominio.admin.RolUsuarioId;

public interface RolUsuarioRepository extends JpaRepository<RolUsuario, RolUsuarioId>{
	
	 @Query("select ru FROM RolUsuario ru WHERE ru.id.codUsuario = :codUsuario")    
		List<RolUsuario> findByCodUsuario(@Param("codUsuario") Long codUsuario);
	    
	    void deleteAllByRolUsuarioId_CodUsuario(Long codUsuario);    
	    
	    <S extends RolUsuario> List<S> saveAll(Iterable<S> entities);

}
