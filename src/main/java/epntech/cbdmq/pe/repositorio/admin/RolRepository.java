package epntech.cbdmq.pe.repositorio.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Rol;

public interface RolRepository extends JpaRepository<Rol, Long>{
	
	Rol findRolByCodRol(Integer codRol);

}
