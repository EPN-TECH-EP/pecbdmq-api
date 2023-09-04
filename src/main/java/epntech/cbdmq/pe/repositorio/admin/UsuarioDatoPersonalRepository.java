package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.query.Param;
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
	
	@Query(value = "select u.cod_usuario, u.nombre_usuario, dp.cod_datos_personales, dp.apellido, dp.nombre, dp.cedula, dp.correo_personal "
			+ "from cbdmq.gen_dato_personal dp, cbdmq.gen_usuario u "
			+ "where dp.cod_datos_personales = u.cod_datos_personales "
			+ "and UPPER(dp.estado) = 'ACTIVO' "
			+ "and u.is_active = true "
			+ "and u.is_not_locked = true "
			+ "and dp.cod_datos_personales = :codDatoPersonal", nativeQuery=true)
	UsuarioDatoPersonal getByCodDatoPersonal(Long codDatoPersonal);

	@Query(value = "select pi.cod_inscripcion, u.cod_usuario, u.nombre_usuario, dp.cod_datos_personales, dp.apellido, dp.nombre, dp.cedula, dp.correo_personal, pc.cod_periodo "
			+ "from cbdmq.pro_inscripcion pi inner join cbdmq.gen_estudiante ge on ge.cod_estudiante = pi.cod_estudiante " +
			"inner join cbdmq.gen_usuario u on ge.cod_datos_personales= u.cod_datos_personales " +
			"inner join cbdmq.gen_dato_personal dp on u.cod_datos_personales = dp.cod_datos_personales " +
			"inner join cbdmq.pro_convocatoria pc on pi.cod_convocatoria = pc.cod_convocatoria "
			+ "where u.is_active = true "
			+ "and u.is_not_locked = true "
			+ "and dp.estado = 'ACTIVO' "
			+ "and pc.estado <> 'FINALIZADO' "
			+ "and dp.cedula =:cedula and pi.aceptado=true", nativeQuery = true)
	UsuarioDatoPersonal getByCedulaProfesionalizacion(String cedula);

	@Query(value = "select pi.cod_inscripcion, u.cod_usuario, u.nombre_usuario, dp.cod_datos_personales, dp.apellido, dp.nombre, dp.cedula, dp.correo_personal, pc.cod_periodo "
			+ "from cbdmq.pro_inscripcion pi inner join cbdmq.gen_estudiante ge on ge.cod_estudiante = pi.cod_estudiante " +
			"inner join cbdmq.gen_usuario u on ge.cod_datos_personales= u.cod_datos_personales " +
			"inner join cbdmq.gen_dato_personal dp on u.cod_datos_personales = dp.cod_datos_personales " +
			"inner join cbdmq.pro_convocatoria pc on pi.cod_convocatoria = pc.cod_convocatoria "
			+ "where u.is_active = true "
			+ "and u.is_not_locked = true "
			+ "and dp.estado = 'ACTIVO' "
			+ "and (LOWER(dp.nombre) like %:nombre% or LOWER(dp.apellido) like %:apellido%) and pi.aceptado=true", nativeQuery = true)
	List<UsuarioDatoPersonal> getByCedulaProfesionalizacionNombreApellido(String nombre, String apellido);

	@Query(value = "select pi.cod_inscripcion, u.cod_usuario, u.nombre_usuario, dp.cod_datos_personales, " +
			"dp.apellido, dp.nombre, dp.cedula, dp.correo_personal, pc.cod_periodo " +
			"from cbdmq.pro_inscripcion pi " +
			"inner join cbdmq.gen_estudiante ge on ge.cod_estudiante = pi.cod_estudiante " +
			"inner join cbdmq.gen_usuario u on ge.cod_datos_personales= u.cod_datos_personales " +
			"inner join cbdmq.gen_dato_personal dp on u.cod_datos_personales = dp.cod_datos_personales " +
			"inner join cbdmq.pro_convocatoria pc on pi.cod_convocatoria = pc.cod_convocatoria " +
			"where u.is_active = true " +
			"and u.is_not_locked = true " +
			"and dp.estado = 'ACTIVO' " +
			"and (dp.correo_personal like %:email% or dp.correo_institucional like %:email%) and pi.aceptado=true", nativeQuery = true)
	List<UsuarioDatoPersonal> getByCedulaProfesionalizacionEmail(String email);
	
}
