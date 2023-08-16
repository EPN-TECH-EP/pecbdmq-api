package epntech.cbdmq.pe.dominio.util.profesionalizacion.repository;

import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProInscripcionesDelegadosDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProInscripcionesDelegadosDatosRepository extends JpaRepository<ProInscripcionesDelegadosDto, Integer> {

    @Query(value = "select id.cod_inscripciones_delegados, id.cod_inscripciones, id.cod_delegados, pi.cod_estudiante, ge.cod_datos_personales, " +
            "g.nombre nombre_estudiante, g.apellido apellido_estudiante, g.correo_personal correo_personal_estudiante, g.cod_datos_personales cod_datos_personales_estudiante , gdp.nombre nombre_delegado, " +
            "gdp.apellido apellido_delegado, gdp.correo_personal correo_personal_delegado, gdp.cod_datos_personales cod_datos_personales_delegado, gdp.cedula cedula_estudiante " +
            "from cbdmq.pro_inscripciones_delegados id inner join cbdmq.pro_delegados pd on pd.cod_delegado = id.cod_delegados " +
            "inner join cbdmq.pro_inscripcion pi on pi.cod_inscripcion = id.cod_inscripciones " +
            "inner join cbdmq.gen_dato_personal gdp on gdp.cod_datos_personales = pd.cod_usuario " +
            "inner join cbdmq.gen_estudiante ge on ge.cod_estudiante = pi.cod_estudiante " +
            "inner join cbdmq.gen_dato_personal g on g.cod_datos_personales = ge.cod_datos_personales " +
            "where pi.cod_convocatoria=:cod_convocatoria", nativeQuery = true)
    List<ProInscripcionesDelegadosDto> findByCodConvocatoria(Integer cod_convocatoria);

    @Query(value = "select id.cod_inscripciones_delegados, id.cod_inscripciones, id.cod_delegados, pi.cod_estudiante, ge.cod_datos_personales, " +
            "g.nombre nombre_estudiante, g.apellido apellido_estudiante, g.correo_personal correo_personal_estudiante, g.cod_datos_personales cod_datos_personales_estudiante , gdp.nombre nombre_delegado, " +
            "gdp.apellido apellido_delegado, gdp.correo_personal correo_personal_delegado, gdp.cod_datos_personales cod_datos_personales_delegado, g.cedula cedula_estudiante " +
            "from cbdmq.pro_inscripciones_delegados id inner join cbdmq.pro_delegados pd on pd.cod_delegado = id.cod_delegados " +
            "inner join cbdmq.pro_inscripcion pi on pi.cod_inscripcion = id.cod_inscripciones " +
            "inner join cbdmq.gen_dato_personal gdp on gdp.cod_datos_personales = pd.cod_usuario " +
            "inner join cbdmq.gen_estudiante ge on ge.cod_estudiante = pi.cod_estudiante " +
            "inner join cbdmq.gen_dato_personal g on g.cod_datos_personales = ge.cod_datos_personales " +
            "where pi.cod_convocatoria=:cod_convocatoria and pd.cod_usuario=:cod_delegado", nativeQuery = true)
    List<ProInscripcionesDelegadosDto> findByCodConvocatoriaAnAndCodDelegados(Integer cod_convocatoria, Integer cod_delegado);
}
