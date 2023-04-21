package epntech.cbdmq.pe.repositorio.admin;

import java.util.Set;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import epntech.cbdmq.pe.dominio.admin.UsuarioDatoPersonal;

@Repository
public interface UsuarioDatoPersonalRepository extends JpaRepository<UsuarioDatoPersonal, Integer> {

	@Query(value="select u.cod_usuario, u.nombre_usuario, dp.cod_datos_personales, dp.apellido, dp.nombre, dp.cedula, dp.correo_personal "
			+ "from cbdmq.gen_usuario u, cbdmq.gen_dato_personal dp "
			+ "where u.cod_datos_personales = dp.cod_datos_personales "
			+ "and u.is_active = true "
			+ "and u.is_not_locked = true "
			+ "and dp.estado = 'ACTIVO'", nativeQuery=true)
	Set<UsuarioDatoPersonal> getUsuarios();
	
	@Query(value="select u.cod_usuario, u.nombre_usuario, dp.cod_datos_personales, dp.apellido, dp.nombre, dp.cedula, dp.correo_personal "
			+ "from cbdmq.gen_usuario u, cbdmq.gen_dato_personal dp "
			+ "where u.cod_datos_personales = dp.cod_datos_personales "
			+ "and u.is_active = true "
			+ "and u.is_not_locked = true "
			+ "and dp.estado = 'ACTIVO' "
			+ "and dp.cedula =:cedula", nativeQuery=true)
	UsuarioDatoPersonal getByCedula(String cedula);
	
}
