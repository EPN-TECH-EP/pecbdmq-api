package epntech.cbdmq.pe.dominio.util.profesionalizacion.repository;

import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProPeriodoEstudianteDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProPeriodoEstudianteDatosRepository extends JpaRepository<ProPeriodoEstudianteDto, Integer> {
    @Query(value = "select pe.cod_periodo_estudiante, pe.cod_datos_personales, pe.cod_periodo, gdp.cedula, gdp.nombre, gdp.apellido, gdp.correo_personal, e.cod_estudiante " +
            "from cbdmq.pro_periodo_estudiante pe inner join cbdmq.gen_dato_personal gdp on gdp.cod_datos_personales = pe.cod_datos_personales " +
            "inner join cbdmq.pro_periodo pp on pe.cod_periodo = pp.cod_periodo inner join cbdmq.gen_estudiante e on gdp.cod_datos_personales = e.cod_datos_personales " +
            "where pe.cod_periodo=:codPeriodo and pe.estado='ACTIVO' and e.codigo_unico_estudiante ilike '%P%'", nativeQuery = true)
    List<ProPeriodoEstudianteDto> getAllByCodPeriodo(Integer codPeriodo);


    /*@Query(value = "Select * from cbdmq.pro_inscripcion " +
            "inner join cbdmq.gen_estudiante ge on ge.cod_estudiante = pro_inscripcion.cod_estudiante " +
            "inner join cbdmq.gen_dato_personal gdp on gdp.cod_datos_personales = ge.cod_datos_personales where gdp.cedula=:cedula", nativeQuery = true)
    List<Usuario> findByCedula(String cedula);*/

/*    @Query(value = "select u.cod_usuario, u.nombre_usuario, dp.cod_datos_personales, dp.apellido, dp.nombre, dp.cedula, dp.correo_personal "
            + "from cbdmq.pro_inscripcion pi inner join cbdmq.gen_estudiante ge on ge.cod_estudiante = pi.cod_estudiante " +
            "inner join cbdmq.gen_usuario u on ge.cod_datos_personales= u.cod_datos_personales " +
            "inner join cbdmq.gen_dato_personal dp on u.cod_datos_personales = dp.cod_datos_personales "
            + " where u.is_active = true "
            + "and u.is_not_locked = true "
            + "and dp.estado = 'ACTIVO' "
            + "and dp.cedula =:cedula", nativeQuery = true)
    UsuarioDatoPersonal getByCedula(String cedula);*/
}
