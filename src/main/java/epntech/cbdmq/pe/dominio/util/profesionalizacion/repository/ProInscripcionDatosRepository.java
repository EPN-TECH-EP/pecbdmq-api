package epntech.cbdmq.pe.dominio.util.profesionalizacion.repository;

import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProInscripcionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProInscripcionDatosRepository extends JpaRepository<ProInscripcionDto, Integer> {
    @Query(value="select p.cod_inscripcion, p.cod_estudiante, p.cod_convocatoria, p.fecha_inscripcion, "
            + "dp.cod_datos_personales, dp.apellido, dp.cedula, dp.nombre, dp.fecha_nacimiento, "
            + "dp.tipo_sangre, dp.sexo, "
            + "(select	gc.nombre from	cbdmq.gen_canton gc	where		UPPER(gc.estado) = 'ACTIVO'		and gc.cod_canton = dp.cod_canton_nacimiento) canton_nacimiento, "
            + "	(select		gc.nombre	from		cbdmq.gen_canton gc	where		UPPER(gc.estado) = 'ACTIVO'		and gc.cod_canton = dp.cod_canton_residencia) canton_residencia, "
            + "dp.calle_principal_residencia, dp.calle_secundaria_residencia, dp.numero_casa, "
            + "dp.colegio, dp.tipo_nacionalidad, dp.num_telef_celular, "
            + "dp.nombre_titulo_segundo_nivel, "
            + "dp.pais_titulo_segundo_nivel, "
            + "dp.ciudad_titulo_segundo_nivel , "
            + "dp.merito_academico_descripcion, dp.merito_deportivo_descripcion, "
            + "(select pr.nombre from cbdmq.gen_provincia pr where UPPER(pr.estado) = 'ACTIVO' and pr.cod_provincia = dp.cod_provincia_nacimiento) provincia_nacimiento, "
            + "(select pr.nombre from cbdmq.gen_provincia pr where UPPER(pr.estado) = 'ACTIVO' and pr.cod_provincia = dp.cod_provincia_residencia) provincia_residencia, "
            + "dp.correo_personal, p.aceptado, p.convalidacion "
            + "from cbdmq.pro_inscripcion p, cbdmq.gen_estudiante e, cbdmq.gen_dato_personal dp "
            + "where p.cod_estudiante = e.cod_estudiante and e.cod_datos_personales = dp.cod_datos_personales "
            + "and UPPER(p.estado) != 'ELIMINADO' "
            + "and UPPER(dp.estado) = 'ACTIVO' "
            + "and p.cod_inscripcion = :codInscripcion", nativeQuery=true)
    Optional<ProInscripcionDto> getProDatosInscripcion(Integer codInscripcion);

    @Query(value="select p.cod_inscripcion, p.cod_estudiante, p.cod_convocatoria, p.fecha_inscripcion, "
            + "dp.cod_datos_personales, dp.apellido, dp.cedula, dp.nombre, dp.fecha_nacimiento, "
            + "dp.tipo_sangre, dp.sexo, "
            + "(select	gc.nombre from	cbdmq.gen_canton gc	where		UPPER(gc.estado) = 'ACTIVO'		and gc.cod_canton = dp.cod_canton_nacimiento) canton_nacimiento, "
            + "	(select		gc.nombre	from		cbdmq.gen_canton gc	where		UPPER(gc.estado) = 'ACTIVO'		and gc.cod_canton = dp.cod_canton_residencia) canton_residencia, "
            + "dp.calle_principal_residencia, dp.calle_secundaria_residencia, dp.numero_casa, "
            + "dp.colegio, dp.tipo_nacionalidad, dp.num_telef_celular, "
            + "dp.nombre_titulo_segundo_nivel, "
            + "dp.pais_titulo_segundo_nivel, "
            + "dp.ciudad_titulo_segundo_nivel , "
            + "dp.merito_academico_descripcion, dp.merito_deportivo_descripcion, "
            + "(select pr.nombre from cbdmq.gen_provincia pr where UPPER(pr.estado) = 'ACTIVO' and pr.cod_provincia = dp.cod_provincia_nacimiento) provincia_nacimiento, "
            + "(select pr.nombre from cbdmq.gen_provincia pr where UPPER(pr.estado) = 'ACTIVO' and pr.cod_provincia = dp.cod_provincia_residencia) provincia_residencia, "
            + "dp.correo_personal, p.aceptado, p.convalidacion "
            + "from cbdmq.pro_inscripcion p, cbdmq.gen_estudiante e, cbdmq.gen_dato_personal dp "
            + "where p.cod_estudiante = e.cod_estudiante and e.cod_datos_personales = dp.cod_datos_personales "
            + "and UPPER(p.estado) != 'ELIMINADO' "
            + "and UPPER(dp.estado) = 'ACTIVO' "
            + "and p.cod_convocatoria = :codConvocatoria", nativeQuery=true)
    List<ProInscripcionDto> findByCodConvocatoria(Integer codConvocatoria);

    @Query(value="select p.cod_inscripcion, p.cod_estudiante, p.cod_convocatoria, p.fecha_inscripcion, "
            + "dp.cod_datos_personales, dp.apellido, dp.cedula, dp.nombre, dp.fecha_nacimiento, "
            + "dp.tipo_sangre, dp.sexo, "
            + "(select	gc.nombre from	cbdmq.gen_canton gc	where		UPPER(gc.estado) = 'ACTIVO'		and gc.cod_canton = dp.cod_canton_nacimiento) canton_nacimiento, "
            + "	(select		gc.nombre	from		cbdmq.gen_canton gc	where		UPPER(gc.estado) = 'ACTIVO'		and gc.cod_canton = dp.cod_canton_residencia) canton_residencia, "
            + "dp.calle_principal_residencia, dp.calle_secundaria_residencia, dp.numero_casa, "
            + "dp.colegio, dp.tipo_nacionalidad, dp.num_telef_celular, "
            + "dp.nombre_titulo_segundo_nivel, "
            + "dp.pais_titulo_segundo_nivel, "
            + "dp.ciudad_titulo_segundo_nivel , "
            + "dp.merito_academico_descripcion, dp.merito_deportivo_descripcion, "
            + "(select pr.nombre from cbdmq.gen_provincia pr where UPPER(pr.estado) = 'ACTIVO' and pr.cod_provincia = dp.cod_provincia_nacimiento) provincia_nacimiento, "
            + "(select pr.nombre from cbdmq.gen_provincia pr where UPPER(pr.estado) = 'ACTIVO' and pr.cod_provincia = dp.cod_provincia_residencia) provincia_residencia, "
            + "dp.correo_personal "
            + "from cbdmq.pro_inscripcion p, cbdmq.gen_estudiante e, cbdmq.gen_dato_personal dp "
            + "where p.cod_estudiante = e.cod_estudiante and e.cod_datos_personales = dp.cod_datos_personales "
            + "and UPPER(p.estado) != 'ELIMINADO' "
            + "and UPPER(dp.estado) = 'ACTIVO' "
            + "and p.aceptado = 1", nativeQuery=true)
    List<ProInscripcionDto> findByAceptado();

}
