package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.admin.Rol;

public interface RolRepository extends JpaRepository<Rol, Long>{
	
	Rol findRolByCodRol(Integer codRol);
	
	Optional<Rol> findByNombreIgnoreCase(String Nombre);

}
